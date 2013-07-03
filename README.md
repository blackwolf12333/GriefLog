
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
* The ArgumentParser we just created.

The SearchTask class also implements Runnable just like GriefLogger. It does some setting up and
loops through all the files and searches.
The searching is done by reading the whole file to a String and splitting that String with each newline.
After that each line is parsed by BaseData to a Data instance that represents the event for that line.
Than it checks if that line comes through all the filters and if so it is added to a list for the results
of the search. When every file is done the results are sorted by time index and the callback is executed.
