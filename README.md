# Why? #

I initialy wrote this plugin as a way to see who broke or placed blocks on my own server,
it was in offline mode thus many griefers thought it was an easy target.
But restoring everything by hand was to much work, so I added rollback abilities and some
more events.
Although I had enough experience to setup a mysql server, I didn't want to do it. That's why
this plugin has no SQL backend. I use plain text as my backend for this plugin, but that does
not harm the servers performance because of the multi threading I added after a while.

# How? #

Some of you might be wondering what this is all about, it is just an explanation how the plugin
works. So lets get started.

## Sessions ##

Every time a player logs in to the server this plugin creates an instance of PlayerSession.
This class provides a wrapper for the normal Player and CommandSender class. This enables me to
automate some things I would otherwise need multiple lines of code for, and that makes everything
to messy I think.

## Logging ##

I log everything in plain text to a file. The files are located in the logs directory which you can
find in your server's root directory. In the logs directory there are some more folders with the
names of your worlds. In these folders you can find the logs of that specific world.
Every time a player does something that my listeners are registrated to, the EventHandler gathers
the nessecary information and creates a new instance of GriefLogger.
GriefLogger is a class that implements Runnable, in it's constructor it creates a new Thread
from itself and starts it. After that the run method starts the log method that actually writes
the data to a file. This is all done in a separate thread so the server will not have to wait
for the write to complete.

## Searching ##

When a player wants to search for something in the logs he can do that through the "/glog search"
command. After that there should follow some arguments about what to search for.
These arguments are the following:
* e: "e" is event, thus what event you want to search for.
* p: "p" is the name of the player you want to search for.
* w: "w" is the name of the world you want to search in.
* b: "b" is a list separated by comma's of the blocks you want to search for.
* t: "t" is how far back you want to search. example: t:2d3h4m30s is search 2 days 3 houres 4 minutes and 30 seconds back.

After that the arguments go through the ArgumentParser class that creates a couple of Filter instances
that the SearchTask class can use. After the ArgumentParser is done we create a new instance of
the SearchTask class with the following arguments in it's constructor:
* The PlayerSession who requests the search.
* The SearchCallback, I'll be talking about that later on.
* The ArgumentParser we just created

The SearchTask class also implements Runnable just like GriefLogger. It does some setting up and
loops through all the files and searches.
The searching is done by reading the whole file to a String and splitting that String with each newline.
After that each line is parsed by BaseData to a Data instance that represents the event for that line.
Than it checks if that line comes through all the filters and if so it is added to a list for the results
of the search. When every file is done the results are sorted by time index and the callback is executed.

### Callback ###

There is only one callback class, SearchCallback. It has and internal enum that defines what type it can be. This can be one of the following:
* SEARCH
* TPTO
* ROLLBACK
* UNDO
* CHEST_SEARCH

When the callback is called it executes the start() function that checks what type this callback is and acts on that. For SEARCH that would be to display the search results to the user. For TPTO that would be to teleport the user to the location that the search ended up with. For ROLLBACK that would be to roll back the found events in the search results. For UNDO this would be to undo a rollback with the events found in the search results, I'll explain undo's later on. And for CHEST_SEARCH it will display the results for the search on a chest to the user with a difference view in an inventory view, it will show what items were taken in the upper half and the items that were added in the lower half.

## Undo ##
Undo is used when a rollback didn't have the desired effect and has to be undone. What is done for this is that the Undo class determines what the arguments of the faulty rollback were and tries to find the same events in the logs. Then it takes those events after being parsed to a BaseData child and calls the undo() function on them. This function will do the reverse of the rollback() function in that class. So for instance with a BlockBreakEvent it would instead of place the broken block back it would remove that block, to "redo" the BlockBreakEvent. But it will not fire a new BlockBreakEvent!