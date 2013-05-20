package tk.blackwolf12333.grieflog.utils;

import java.util.Map;
import java.util.Map.Entry;
 
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
 
public class InventoryStringDeSerializer {
    public static String InventoryToString (Inventory invInventory)
    {
        String serialization = invInventory.getSize() + ";";
        for (int i = 0; i < invInventory.getSize(); i++)
        {
            ItemStack is = invInventory.getItem(i);
            if (is != null)
            {
                serialization += i + "#" + itemToString(is) + ";";
            }
        }
        return serialization;
    }
   
    public static Inventory StringToInventory (String invString)
    {
        String[] serializedBlocks = invString.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));
       
        for (int i = 1; i < serializedBlocks.length; i++)
        {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]);
           
            if (stackPosition >= deserializedInventory.getSize())
            {
                continue;
            }
           
            ItemStack is = null;
            Boolean createdItemStack = false;
           
            String[] serializedItemStack = serializedBlock[1].split(":");
            for (String itemInfo : serializedItemStack)
            {
                String[] itemAttribute = itemInfo.split("=");
                if (itemAttribute[0].equals("t"))
                {
                    is = new ItemStack(Material.getMaterial(itemAttribute[1]));
                    createdItemStack = true;
                }
                else if (itemAttribute[0].equals("d") && createdItemStack)
                {
                    is.setDurability(Short.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("a") && createdItemStack)
                {
                    is.setAmount(Integer.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("e") && createdItemStack)
                {
                    is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
                }
            }
            deserializedInventory.setItem(stackPosition, is);
        }
       
        return deserializedInventory;
    }
    
    public static String itemToString(ItemStack is) {
    	String serializedItemStack = new String();
    	if(is != null) {
    		String isType = is.getType().toString();
    		serializedItemStack += "t=" + isType;
        	
        	if (is.getDurability() != 0)
        	{
        		String isDurability = String.valueOf(is.getDurability());
        		serializedItemStack += ":d=" + isDurability;
        	}
        	
        	if (is.getAmount() != 1)
        	{
        		String isAmount = String.valueOf(is.getAmount());
        		serializedItemStack += ":a=" + isAmount;
        	}
        	
        	Map<Enchantment,Integer> isEnch = is.getEnchantments();
        	if (isEnch.size() > 0)
        	{
        		for (Entry<Enchantment,Integer> ench : isEnch.entrySet())
        		{
        			serializedItemStack += ":e=" + ench.getKey().getId() + "=" + ench.getValue();
        		}
        	}
    	}
        return serializedItemStack;
    }
    
    public static SerializedItem stringToItem(String str) {
    	ItemStack is = null;
        Boolean createdItemStack = false;
        int slot = getSlot(str);
        
        String[] serializedItemStack = str.split(":");
        for (String itemInfo : serializedItemStack)
        {
            String[] itemAttribute = itemInfo.split("=");
            if (itemAttribute[0].equals("t"))
            {
                is = new ItemStack(Material.getMaterial(itemAttribute[1]));
                createdItemStack = true;
            }
            else if (itemAttribute[0].equals("d") && createdItemStack)
            {
                is.setDurability(Short.valueOf(itemAttribute[1]));
            }
            else if (itemAttribute[0].equals("a") && createdItemStack)
            {
                is.setAmount(Integer.valueOf(itemAttribute[1]));
            }
            else if (itemAttribute[0].equals("e") && createdItemStack)
            {
                is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
            }
        }
        return new SerializedItem(str, is, slot); //TODO: Test this: implement the stringToItem function in InventoryStringDeSerializer
    }

	private static int getSlot(String str) {
		int slot = 0;
		try {
			slot = Integer.parseInt(str.substring(0, 2));
		} catch(NumberFormatException e) {
			slot = Integer.parseInt(str.substring(0, 1));
		}
		return slot;
	}
}
