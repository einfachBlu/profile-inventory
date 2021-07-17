package de.blu.profileinventory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.blu.itemstacksaver.ItemStackSaver;
import de.blu.profileinventory.listener.ProfileLoginListener;
import de.blu.profileinventory.listener.ProfileLogoutListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public final class ProfileInventoryPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                bind(JavaPlugin.class).toInstance(ProfileInventoryPlugin.this);
                bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
                bind(ItemStackSaver.class).toInstance(ItemStackSaver.getInstance());
              }
            });

    injector.injectMembers(this);
    this.init(injector);
  }

  private void init(Injector injector) {
    // Register Listener
    Bukkit.getServer()
        .getPluginManager()
        .registerEvents(injector.getInstance(ProfileLoginListener.class), this);
    Bukkit.getServer()
        .getPluginManager()
        .registerEvents(injector.getInstance(ProfileLogoutListener.class), this);
  }
}
