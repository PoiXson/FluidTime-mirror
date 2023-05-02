package com.poixson.fluidtime;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;


public class FluidTimeAPI {
	protected static final Logger LOG = Logger.getLogger("Minecraft");

	protected static final String NAME  = "FluidTime";
	protected static final String CLASS = "com.poixson.fluidtime.FluidTimePlugin";

	protected final FluidTimePlugin plugin;

	protected static final AtomicInteger errcount_PluginNotFound = new AtomicInteger(0);



	public static FluidTimeAPI GetAPI() {
		// existing instance
		{
			final ServicesManager services = Bukkit.getServicesManager();
			final FluidTimeAPI api = services.load(FluidTimeAPI.class);
			if (api != null)
				return api;
		}
		// load api
		try {
			if (Class.forName(CLASS) == null)
				throw new ClassNotFoundException(CLASS);
			final PluginManager manager = Bukkit.getPluginManager();
			final Plugin plugin = manager.getPlugin(NAME);
			if (plugin == null) throw new RuntimeException(NAME+" plugin not found");
			return new FluidTimeAPI(plugin);
		} catch (ClassNotFoundException e) {
			if (errcount_PluginNotFound.getAndIncrement() < 10)
				LOG.severe("Plugin not found: "+NAME);
			return null;
		}
	}

	protected FluidTimeAPI(final Plugin p) {
		if (p == null) throw new NullPointerException();
		this.plugin = (FluidTimePlugin) p;
	}



}
