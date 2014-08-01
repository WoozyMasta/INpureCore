package appeng.api.networking.events;

/**
 * Posted by storage devices to inform AE to refreash its storage structure.
 * <p/>
 * This is done in cases such as a storage cell being removed or added to a
 * drive.
 * <p/>
 * you do not need to send this event when your node is added / removed from the
 * grid.
 */
public class MENetworkCellArrayUpdate extends MENetworkEvent {

}
