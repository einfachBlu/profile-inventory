package de.blu.profileinventory.listener;

import de.blu.itemstacksaver.ItemStackSaver;
import de.blu.profilemanager.event.ProfileLogoutEvent;
import de.blu.profilesystem.data.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ProfileLogoutListener implements Listener {

  @Inject private ItemStackSaver itemStackSaver;

  @EventHandler
  public void onProfileLogout(ProfileLogoutEvent e) {
    Player player = e.getPlayer();
    Profile profile = e.getProfile();

    ItemStack[] contents = player.getInventory().getContents().clone();
    ItemStack[] armorContents = player.getInventory().getArmorContents().clone();
    ItemStack[] extraContents = player.getInventory().getExtraContents().clone();

    String baseKey = "profileinventory." + profile.getId().toString();

    this.itemStackSaver.setItemStacksAsync(baseKey + ".content", contents);
    this.itemStackSaver.setItemStacksAsync(baseKey + ".armor", armorContents);
    this.itemStackSaver.setItemStacksAsync(baseKey + ".extra", extraContents);
  }
}
