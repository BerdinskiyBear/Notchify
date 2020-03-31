## Configurations info
In this document you can find description for configuration parameters in configuration file for **Notchify** mod.
Configuration file usually can be found in `.minecraft/config` folder and this file is called `notchify.json`.
1. ##### `enable_enchanting_using_anvil`
    Allows to enable or disable enchanting of golden apples using anvils. If set to `true` enchanting using anvils is enabled, if set to `false` enchanting of golden apples using anvil is disabled.
   * Default value: `true`
2. ##### `enable_enchanting_using_enchanting_table`
    Same as previous setting but for enchanting tables.
    * Default value: `true`
3. ##### `enchanted_golden_apple_cost`
    Specifies amount of levels required to enchant a golden apple using an anvil.
This parameter also influences chance of enchantment when using enchantment table (higher the cost lower the chance).
Be aware, that if you set this parameter over 39 will make it impossible to enchant golden apples using anvils because of the vanilla limitation.
To circumvent this issue I suggest using [Anvil Fix Mod](https://www.curseforge.com/minecraft/mc-mods/anvil-fix).
    * Default value: `39`
4. ##### `creative_player_always_succeeds`
    If set to `true` creative players will always succeed when enchanting a golden apple on the enchantment table.
If set to `false` creative players will have same chance of enchanting a golden apple.
    * Default value: `true`
5. ##### `survival_player_always_succeeds`
    If set to `true` ANY PLAYER will have 100% chance enchanting a golden apple on the enchantment table.
    * Default value: `false`
6. ##### `enchanting_chance_modifier`
    This parameter changes how likely it is to enchant a golden apple relative to default.
By default chance of enchanting of a golden apple is about 7.6%.
For example, if you set this modifier to `6.5` 50% of all attempts will be successful.
    * Default value: `1.0`
    
If one or several of these parameters are not present, default value for missing parameters will be used.
