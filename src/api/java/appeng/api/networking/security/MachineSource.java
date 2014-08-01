package appeng.api.networking.security;

public class MachineSource extends BaseActionSource {

    public final IActionHost via;

    public MachineSource(IActionHost v) {
        via = v;
    }

    @Override
    public boolean isMachine() {
        return true;
    }

}
