package net.torosamy.safetradeywy.pojo.trade;


import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public class TradeInventory {
    public final static int[] senderCannotSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25, 26, 27, 31, 32, 33, 34, 35, 36, 40, 41, 42, 43, 44, 45, 46, 47, 49, 50, 51, 52, 53};
    public final static int[] receiverCannotSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 17, 18, 19, 20, 21, 22, 26, 27, 28, 29, 30, 31, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 51, 52, 53};

    public final static int[] senderCanSlots = {10, 19, 28, 37, 11, 20, 29, 38, 12, 21, 30, 39};
    public final static int[] receiverCanSlots = {14, 23, 32, 41, 15, 24, 33, 42, 16, 25, 34, 43};

    public final static int[] grayFrameSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 53, 13, 22, 31, 40, 46, 47, 51, 52};
    public final static int[] redButtonSlots = {48, 50};
    public final static int confirmButtonSlot = 49;
    public final static ItemStack redButtonItem = createRedButtonItem();
    public final static ItemStack greenButtonItem = createGreenButtonItem();
    public final static ItemStack grayFrameItem = createGrayFrameItem();
    private ItemStack yellowConfirmItem;
    private Inventory inventory;
    private Trade trade;
    private Boolean finished;



    public Inventory getInventory() {
        return inventory;
    }

    public TradeInventory(Trade trade) {
        this.trade = trade;
        this.finished = false;
        this.yellowConfirmItem = createYellowConfirmItem();
        Inventory inventory = Bukkit.createInventory(null, 54, "安全交易");
        for (int grayFrameSlot : grayFrameSlots) inventory.setItem(grayFrameSlot, grayFrameItem);
        for (int redButtonSlot : redButtonSlots) inventory.setItem(redButtonSlot, redButtonItem);
        inventory.setItem(confirmButtonSlot, this.yellowConfirmItem);
        this.inventory = inventory;

    }


    public void updateYellowConfirmItem(int second) {
        ItemMeta itemMeta = this.yellowConfirmItem.getItemMeta();
        if (second < 0) itemMeta.setDisplayName(MessageUtils.text("&e等待双方结束准备"));
        else itemMeta.setDisplayName(MessageUtils.text("&e完成倒计时 " + second + " 秒"));


        this.yellowConfirmItem.setItemMeta(itemMeta);
        this.inventory.setItem(confirmButtonSlot, this.yellowConfirmItem);
    }
    public void updateConfirmItem(Integer slot) {
        if (inventory.getItem(slot).getType() == Material.RED_STAINED_GLASS_PANE) inventory.setItem(slot, TradeInventory.greenButtonItem);
        else inventory.setItem(slot, TradeInventory.redButtonItem);
        trade.updateConfirm(slot);
    }
    public void falseConfirmItem(Integer slot) {
        if (slot == 48) trade.setConfirmSender(false);
        else trade.setConfirmReceiver(false);
        trade.getTradeInventory().getInventory().setItem(slot,TradeInventory.redButtonItem);
    }
    private static ItemStack createGrayFrameItem() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.text("&e装饰线"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack createRedButtonItem() {
        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.text("&c点击以结束准备"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack createGreenButtonItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.text("&a点击以中断交易"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack createYellowConfirmItem() {
        ItemStack itemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.text("&e等待双方结束准备"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
