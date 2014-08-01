package appeng.api.config;

public enum PowerUnits {
    AE("gui.appliedenergistics2.units.appliedenergstics"), // Native Units - AE Energy
    MJ("gui.appliedenergistics2.units.buildcraft"), // BuildCraft - Minecraft Joules
    EU("gui.appliedenergistics2.units.ic2"), // IndustrialCraft 2 - Energy Units
    KJ("gui.appliedenergistics2.units.universalelectricity"), // Universal Electricity - KiloJoules
    WA("gui.appliedenergistics2.units.rotarycraft"), // RotaryCraft - Watts
    RF("gui.appliedenergistics2.units.thermalexpansion"); // ThermalExpansion - Redstone Flux
    /**
     * unlocalized name for the power unit.
     */
    final public String unlocalizedName;
    /**
     * please do not edit this value, it is set when AE loads its config files.
     */
    public double conversionRatio = 1.0;

    private PowerUnits(String un) {
        unlocalizedName = un;
    }

    /**
     * do power conversion using AE's conversion rates.
     * <p/>
     * Example: PowerUnits.EU.convertTo( PowerUnits.AE, 32 );
     * <p/>
     * will normally returns 64, as it will convert the EU, to AE with AE's power settings.
     *
     * @param target
     * @param value
     * @return value converted to target units, from this units.
     */
    public double convertTo(PowerUnits target, double value) {
        return (value * conversionRatio) / target.conversionRatio;
    }

}