## Configurations info
In this document you can find description for configuration parameters in configuration file for **Notchify** mod.
If this mod is installed on client, Notchify Mod Config menu is available via [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu).

Configuration file is a JSON file. It usually can be found in `.minecraft/config` folder and this file is called `notchify.json`.
If one or several of configurable parameters or subparameters are not present, default value for missing parameters will be used.
All the configurable parameters:
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
If set to `false` creative players will have same chance of enchanting a golden apple as other players.
    * Default value: `true`
5. ##### `survival_player_always_succeeds`
    If set to `true` ANY PLAYER will have 100% chance enchanting a golden apple on the enchantment table.
    * Default value: `false`
6. ##### `enchanting_chance_modifier`
    This parameter changes how likely it is to enchant a golden apple relative to default.
By default chance of enchanting of a golden apple is about 7.6%.
For example, if you set this modifier to `6.5` 50% of all attempts will be successful.
    * Default value: `1.0`
7. ##### `can_golden_apple_vanish`
    This parameter determines whether golden apple can disappear if enchanting fails or not.
    * Default value: `true`
8. ##### `vanishing_chance`
    This parameter sets the chance of a golden apple disappearing if enchanting fails.
    * Default value: `0.9`
9. ##### `can_enchanted_golden_apple_become_cursed`
    This parameter allows successfully enchanted golden apples to also be cursed.
    * Default value: `true`
10. ##### `chance_of_curse`
    This parameter sets the chance of a golden apple becoming cursed when enchanted successfully.
    * Default value: `0.1`
11. ##### `anvil_requires_secondary_item`
    This parameter determines whether a second item is required or not when creating enchanted golden apples using anvil.
    * Default value: `true`
12. ##### `anvil_secondary_item_id`
    This parameter sets the item that a player has to combine with a golden apple on an anvil to get enchanted golden apple.
    Parameter has to be a [namespaced ID](https://minecraft.gamepedia.com/Namespaced_ID) of the item surrounded with double quotes.
    * Default value: `"minecraft:nether_star"`
13. ##### `anvil_secondary_item_amount`
    This parameter sets the amount of secondary item one will need to enchant a golden apple.
    * Default value: `1`
14. ##### `anvil_secondary_item_nbt_enabled`
    This parameter determines whether NBT of secondary item is considered.
    * Default value: `false`
15. ##### `anvil_secondary_item_nbt`
    This string contains a text representation of a compound tag with tags that secondary item must have in its
    [data](https://minecraft.gamepedia.com/Player.dat_format#General_Tags) [tag](https://minecraft.gamepedia.com/Commands#Data_tags).
    Symbols `"` and `\ ` must be escaped. For example data tag `{Potion: "minecraft:strong_regeneration"}` must be
    written `"{Potion: \"minecraft:strong_regeneration\"}"`.
    * Default value: `"{}"`
16. ##### `can_grindstone_remove_enchantment`
    This setting allows one to toggle the ability to remove enchantment from the enchanted golden apple.
    Why would you need that I don't now. But curses from poorly enchanted apple can be removed this way.
    * Default value: `true`
17. ##### `grinding_xp_multiplier`
    This multiplier changes the maximum amount of experience (points, not levels) that can be obtained from one enchanted golden apple.
    * Default value: `0.1`
18. ##### `will_cursed_apple_poison`
    This parameter enables side effects one gets after eating cursed enchanted apple.
    * Default value: `true`
19. ##### `cursed_apple_poison_effects`
    This list contains all the effects that cursed enchanted golden apple will give the consumer of apple.
    The list (square brackets) describes every status (potion) effect (curly brackets) in three parameters:
    * `"statusEffectId"` is a string with a namespaced ID of the status effect;
        * Default value: `"minecraft:nausea"`
    * `"duration"` is an integer that tells the duration of the effect **in game ticks** (20 game ticks = 1 second)
        * Default value: `0`
    * `"amplifier"` is an integer that tells the level of the effect. Starts at `0`, so for level 3 effect this value would be `2`.
        * Default value: `0`

    If ID parameter of an effect is incorrect, empty or missing, that effect would be ignored.
    * Default value: `[{"statusEffectId": "minecraft:nausea", "duration": 3000, "amplifier": 0}]`
