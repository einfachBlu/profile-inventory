package de.blu.profileinventory.listener;

import de.blu.itemstacksaver.ItemStackSaver;
import de.blu.profilemanager.event.ProfileLoginEvent;
import de.blu.profilesystem.data.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ProfileLoginListener implements Listener {

  @Inject private ItemStackSaver itemStackSaver;

  @EventHandler
  public void onProfileLogin(ProfileLoginEvent e) {
    Player player = e.getPlayer();
    Profile profile = e.getProfile();

    String baseKey = "profileinventory." + profile.getId().toString();

    player.getInventory().clear();
    this.itemStackSaver.getItemStacksAsync(
        baseKey + ".content",
        itemStacks -> {
          if (itemStacks == null) {
            return;
          }

          player.getInventory().setContents(itemStacks);
        });

    this.itemStackSaver.getItemStacksAsync(
        baseKey + ".armor",
        itemStacks -> {
          if (itemStacks == null) {
            return;
          }

          player.getInventory().setArmorContents(itemStacks);
        });

    this.itemStackSaver.getItemStacksAsync(
        baseKey + ".extra",
        itemStacks -> {
          if (itemStacks == null) {
            return;
          }

          player.getInventory().setExtraContents(itemStacks);
        });
  }
}
