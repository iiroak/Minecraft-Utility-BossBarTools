# Boss Bar Tools (Vibe Coding, I don't feel like doing it from scratch)

A simple and configurable Fabric mod for Minecraft 1.21.4 that allows you to hide boss bars completely or toggle their visibility on demand.

## 🌟 Features

- **Complete Boss Bar Hiding**: Hides all boss bars (Ender Dragon, Wither, custom boss bars, etc.)
- **Advanced Filtering System**: Hide specific boss bars based on their title text
- **Flexible Matching Modes**: Choose between "Contains" and "Exact Match" filtering
- **Toggle Functionality**: Press a configurable key (default: `H`) to toggle boss bars on/off
- **Configuration Menu**: Easy-to-use configuration screen via Mod Menu with filtering options
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
- You can change this keybind in `Options > Controls > Key Binds > Boss Bar Tools`

### Configuration Menu
1. Install **Mod Menu** (if not already installed)
2. Go to `Mods` → `Boss Bar Tools` → `Config`
3. **General Settings**:
   - **Hide Boss Bars**: Master toggle for hiding boss bars
   - **Enable Filtering**: When enabled, only filtered boss bars are hidden. When disabled, ALL boss bars are hidden
   - **Show Toggle Messages**: Enable/disable confirmation messages when toggling
   - **Enable Debug Logging**: Show detailed debug information in console (disabled by default)

4. **Exact Match Filters** Tab:
   - Hide boss bars whose titles **exactly match** your specified strings
   - Case-insensitive matching
   - Perfect for hiding specific boss bars by their exact name

5. **Contains Filters** Tab:
   - Hide boss bars whose titles **contain** any of your specified strings
   - Case-insensitive matching  
   - Great for hiding categories of boss bars (e.g., all fishing-related)

6. **Regex Filters** Tab:
   - Hide boss bars whose titles match your **regular expression patterns**
   - Advanced users only - supports full regex syntax
   - Most flexible option for complex filtering rules

### Filtering Examples

**Exact Match Examples:**
- `"Ender Dragon"` → Hides only boss bars titled exactly "Ender Dragon"
- `"Wither"` → Hides only boss bars titled exactly "Wither"

**Contains Examples:**
- `"fish"` → Hides "Big Fish", "Fishing Progress", "Goldfish Health", etc.
- `"dragon"` → Hides "Ender Dragon", "Dragon Fight", "Ice Dragon", etc.
- `"Loading"` → Hides any boss bar with "Loading" in the title

**Regex Examples:**
- `"^.*[Ff]ish.*$"` → Matches any title containing "fish" or "Fish"
- `"\\d+%"` → Matches titles with percentages like "Loading 75%"
- `"^(Ender Dragon|Wither)$"` → Matches exactly "Ender Dragon" OR "Wither"

### Manual Configuration
The mod creates a configuration file at `config/bossbarkiller.json` that you can edit directly:

```json
{
  "hideBossBars": true,
  "showToggleMessage": true,
  "enableDebugLogging": false,
  "enableFiltering": false,
  "exactMatchFilters": [
    "Ender Dragon",
    "Wither"
  ],
  "containsFilters": [
    "fish",
    "loading",
    "progress"
  ],
  "regexFilters": [
    "^.*\\d+%.*$",
    "^(Boss|Mini-Boss): .*"
  ]
}
```

**Configuration Options:**
- `hideBossBars`: Master toggle for hiding boss bars
- `showToggleMessage`: Show messages when toggling via keybind  
- `enableDebugLogging`: Show detailed debug information in console (default: false)
- `enableFiltering`: Enable title-based filtering (when false, hides all boss bars)
- `exactMatchFilters`: Array of strings for exact title matching
- `containsFilters`: Array of strings for partial title matching
- `regexFilters`: Array of regular expression patterns for advanced matching

## 🔧 Technical Details

This mod uses **Mixin injection** to intercept the boss bar rendering process in `BossBarHud.render()`. The implementation includes two levels of filtering:

1. **Global Filtering**: When filtering is disabled, all boss bars are hidden by cancelling the main render method
2. **Individual Filtering**: When filtering is enabled, each boss bar's title is checked against the filter strings using the `renderBossBar()` method injection

### Filtering Logic
The mod uses a **two-stage filtering system** to completely eliminate boss bars and prevent empty spaces:

**Stage 1 - Collection Filtering (`@Redirect`):**
- Intercepts the boss bar collection before any rendering calculations
- Removes filtered boss bars from the source collection entirely
- Prevents empty spaces and ghost titles from appearing

**Stage 2 - Safety Filter (`@Inject`):**
- Double-checks individual boss bar rendering as a safety measure
- Catches any boss bars that might slip through the collection filter

**Filter Processing Order:**
1. **Exact Match Filters**: Uses `String.equals()` with case-insensitive comparison
2. **Contains Filters**: Uses `String.contains()` with case-insensitive comparison  
3. **Regex Filters**: Uses `String.matches()` with full regex pattern support

**Filter Evaluation:**
- Boss bar is hidden if it matches ANY filter from ANY category
- Invalid regex patterns are automatically skipped with error logging
- All text matching (except regex) is case-insensitive
- Empty filter lists are ignored
- **Complete removal**: No empty spaces or titles remain when boss bars are filtered

### Implementation Inspiration

The core boss bar hiding functionality was inspired by the "No Render" module from **Meteor Client**, an open-source utility mod. Our implementation extends this concept with advanced title-based filtering capabilities.

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
3. Check the game logs for any error messages containing "Boss Bar Tools"
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