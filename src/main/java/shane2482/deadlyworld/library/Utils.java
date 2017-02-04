package shane2482.deadlyworld.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemStack;

public class Utils {
	private static Logger logger;

	public static Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MOD_ID);
		}
		return logger;
	}
	
	public static ItemStack validateCopy(ItemStack stack){
        if(isValid(stack)){
            return stack.copy();
        }
        else{
            return null;
        }
    }

    public static ItemStack validateCheck(ItemStack stack){
        if(isValid(stack)){
            return stack;
        }
        else{
            return null;
        }
    }

    public static boolean isValid(ItemStack stack){
        return stack != null && !ItemStack.areItemStacksEqual(stack, null) && stack.stackSize > 0 && stack.getItem() != null;
    }
    
    public static int getStackSize(ItemStack stack){
        if(!isValid(stack)){
            return 0;
        }
        else{
            return stack.stackSize;
        }
    }

    public static ItemStack setStackSize(ItemStack stack, int size){
        return setStackSize(stack, size, false);
    }

    public static ItemStack setStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        if(size <= 0){
            if(isValid(stack) && containerOnEmpty){
                return stack.getItem().getContainerItem(stack);
            }
            else{
                return null;
            }
        }
        stack.stackSize = size;
        return stack;
    }

    public static ItemStack addStackSize(ItemStack stack, int size){
        return addStackSize(stack, size, false);
    }

    public static ItemStack addStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        return setStackSize(stack, getStackSize(stack)+size, containerOnEmpty);
    }

}
