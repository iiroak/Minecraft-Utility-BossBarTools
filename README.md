# Boss Bar Killer

A simple and configurable Fabric mod for Minecraft 1.21.4 that allows you to hide boss bars completely or toggle their visibility on demand.

## 🌟 Features

- **Complete Boss Bar Hiding**: Hides all boss bars (Ender Dragon, Wither, custom boss bars, etc.)
- **Toggle Functionality**: Press a configurable key (default: `H`) to toggle boss bars on/off
- **Configuration Menu**: Easy-to-use configuration screen via Mod Menu
- **Persistent Settings**: Your preferences are automatically saved
- **Multilingual Support**: Available in English and Spanish
- **Lightweight**: Minimal performance impact using efficient Mixin injection

## 🚀 Installation

### Requirements
- **Minecraft 1.21.4**
- **Fabric Loader** (0.17.3 or newer)
- **Fabric API**
- **Mod Menu** (recommended for configuration access)

### Steps
1. Download the latest release from the releases page
2. Place `bossbarkiller-1.0.0.jar` in your `mods` folder
3. Launch Minecraft with Fabric

## ⚙️ Usage

### Quick Toggle
- Press `H` key in-game to instantly toggle boss bar visibility
- A confirmation message will appear in the action bar
- You can change this keybind in `Options > Controls > Key Binds > Boss Bar Killer`

### Configuration Menu
1. Install **Mod Menu** (if not already installed)
2. Go to `Mods` → `Boss Bar Killer` → `Config`
3. Available options:
   - **Hide Boss Bars**: Enable/disable boss bar hiding
   - **Show Toggle Messages**: Enable/disable confirmation messages when toggling

### Manual Configuration
The mod creates a configuration file at `config/bossbarkiller.json` that you can edit directly:

```json
{
  "hideBossBars": true,
  "showToggleMessage": true
}
```

## 🔧 Technical Details

This mod uses **Mixin injection** to intercept the boss bar rendering process in `BossBarHud.render()`. When boss bar hiding is enabled, the rendering call is cancelled before any boss bars are drawn to the screen.

### Implementation Inspiration

The core boss bar hiding functionality was inspired by the "No Render" module from **Meteor Client**, an open-source utility mod. While this implementation is completely rewritten for our specific use case, we acknowledge and appreciate the Meteor Client team's open-source contributions to the Minecraft modding community.

- **Meteor Client**: https://github.com/MeteorDevelopment/meteor-client
- **License**: GNU General Public License v3.0
- **Specific inspiration**: Boss bar hiding technique from the No Render module

## 📄 License & Credits

### This Project
This mod is released under the **Creative Commons Zero v1.0 Universal (CC0-1.0)** license, making it completely free and open source.

### Acknowledgments
- **Meteor Client Development Team** - For the open-source boss bar hiding implementation that inspired this mod
- **Fabric Team** - For the excellent modding framework
- **Shedaniel** - For Cloth Config API used in the configuration system
- **TerraformersMC** - For Mod Menu integration

## 🛠️ Development

### Building from Source
```bash
git clone <repository-url>
cd bossbarkiller-template-1.21.4
./gradlew build
```

The compiled mod will be available in `build/libs/bossbarkiller-1.0.0.jar`

### Project Structure
```
src/
├── main/java/iroak/bossbar/
│   ├── BossBarKiller.java          # Main mod class
│   └── config/
│       └── BossBarKillerConfig.java # Configuration handling
└── client/java/iroak/bossbar/
    ├── BossBarKillerClient.java     # Client-side initialization
    ├── config/
    │   └── ModMenuIntegration.java  # Mod Menu integration
    └── mixin/client/
        └── BossBarHiderMixin.java   # Core boss bar hiding logic
```

## 🐛 Issues & Support

If you encounter any issues or have suggestions:
1. Check that you have all required dependencies installed
2. Verify your Minecraft and mod versions are compatible
3. Check the game logs for any error messages containing "Boss Bar Killer"
4. Open an issue on the project repository with detailed information

## 🤝 Contributing

Contributions are welcome! This project is open source and free for anyone to modify, distribute, or improve upon.

## 📚 FAQ

**Q: Will this work with modded boss bars?**
A: Yes! The mod intercepts the boss bar rendering system itself, so it works with boss bars from any mod.

**Q: Does this affect performance?**
A: No, the mod has minimal performance impact. It only adds a small configuration check before boss bar rendering.

**Q: Can I use this on servers?**
A: This is a client-side only mod, so it works on any server. Other players will still see boss bars normally.

**Q: Is this compatible with other mods?**
A: Yes, this mod is designed to be compatible with other mods. It only affects boss bar rendering.

---

**Made with ❤️ for the Minecraft community**

*If you find this mod useful, consider supporting the open-source projects that made it possible!*