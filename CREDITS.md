# Credits and Acknowledgments

## Meteor Client Inspiration

This mod's core boss bar hiding functionality was inspired by the **No Render module** from Meteor Client, an open-source Minecraft utility mod.

### Meteor Client Details
- **Project**: Meteor Client
- **Repository**: https://github.com/MeteorDevelopment/meteor-client
- **License**: GNU General Public License v3.0
- **Developers**: MeteorDevelopment Team
- **Specific Feature**: Boss bar hiding in the "No Render" module

### Implementation Notes

While our implementation is completely rewritten and adapted for this specific mod's needs, the core concept of using Mixin injection to cancel boss bar rendering was inspired by Meteor Client's approach. We acknowledge and appreciate their open-source contribution to the Minecraft modding community.

**Differences in our implementation:**
- Simplified to focus only on boss bar hiding
- Added configuration system with persistent settings
- Integrated with Mod Menu for easy configuration
- Added toggle functionality with keybind support
- Implemented as a standalone lightweight mod

### Original Meteor Client Code Reference

The original boss bar hiding logic in Meteor Client can be found in their No Render module. Our implementation follows a similar pattern but is adapted for our specific use case and configuration system.

## Other Acknowledgments

### Development Tools & Libraries
- **Fabric Loader & API** - The foundation that makes this mod possible
- **Cloth Config API** by shedaniel - For the configuration system
- **Mod Menu** by TerraformersMC - For configuration screen integration
- **Mixin** - For the bytecode injection framework

### Community
- **Fabric Community** - For documentation, examples, and support
- **Minecraft Modding Community** - For shared knowledge and open-source practices
- **All contributors** to open-source Minecraft mods who share their code and knowledge

## License Compatibility

This project (Boss Bar Tools) is released under CC0-1.0 (Public Domain), making it completely free and open source. While inspired by GPL-licensed code from Meteor Client, our implementation is original and does not include any copyrighted code from Meteor Client.

The inspiration and concepts are used under the principles of fair use and independent implementation, similar to how many programming concepts and algorithms are shared across the software development community.

---

**We believe in open source and giving credit where credit is due. Thank you to all the developers who make their code freely available for learning and inspiration!**