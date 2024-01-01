package hw1;

/**
 * CameraBattery is a class that models a removable camera battery
 * The camera can be charged by the camera when connected to a USB port and by a external charger
 * CameraBatter will keep track of the charge of the battery and react to change
 * @author cadebradford
 *
 */


public class CameraBattery {
	/**Stores the number of charger settings available
	 * @author cadebradford
	 */
	public static final int NUM_CHARGER_SETTINGS = 4; 
	/**
	 * Stores the default charge rate 
	 * @author cadebradford
	 *
	 */
	public static final double CHARGE_RATE = 2.0; 
	/**
	 * Stores the default power consumption of the camera
	 * @author cadebradford
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0; 
	/**\
	 * Allows user to input thier own power consumption for camera and stores that value here
	 * @author cadebradford
	 */
	private double powerConsumption = 1.0;
	/**
	 * Stores the battery charge throughout the program
	 * @author cadebradford
	 */
	
	private double batteryCharge;
	/**
	 * Stores the max capacity for the battery at all times
	 * @author cadebradford
	 */
	private double batteryMaxCapacity;
	/**
	 * Stores the current charger setting the user is on
	 * @author cadebradford
	 */
	private int chargerSetting = 0;
	/**
	 * keeps track of the total drain of the battery
	 * @author cadebradford
	 */
	private int totalDrain = 0;
	/**
	 * keeps track of the batterys state
	 * 0 is disconnnected from everything 1 is connected to external charger and 2 is when in camera
	 * @author cadebradford
	 */
	private int batteryState;
	/**
	 * Creates the CameraBattery and sets the starting charge
	 * @param batteryStartingCharge
	 * intakes the starting charge of the battery and will be assigned to batteryCharge as long as it is not higher than the capacity
	 * @param batteryCapacity
	 * intakes the maximum capacity of the battery
	 * @author cadebradford
	 */
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		batteryCharge = Math.min(batteryStartingCharge, batteryCapacity);
		batteryMaxCapacity = batteryCapacity;
		batteryState = 0;
	}
	/**
	 * Indicates the user has pressed the setting button one time on the external charger
	 *  The charge setting increments by one or if already at the maximum setting wraps around to setting 0
	 * @author cadebradford
	 */
	public void buttonPress() {
		chargerSetting += 1;
		chargerSetting %= NUM_CHARGER_SETTINGS;
		
	}
	/**
	 * The method returns the actual amount the battery has been charged. 
	 * The amount of charging in milliamp-hours (mAh) is the number minutes times the 
CHARGE_RATE constant. However, charging cannot exceed the capacity of the battery 
connected to the camera, or no charging if the battery is not connected. 
	 * @param minutes
	 * intakes minutes battery has been in the camera while connected to the USB
	 * @return 
	 * returns the amount the battery was charged
	 * @author cadebradford
	 */
	public double cameraCharge(double minutes) {
		double batteryHolder = batteryCharge;
		double tempCharge = batteryCharge + ((minutes)*(CHARGE_RATE)*(batteryState /2));
		batteryCharge = Math.min(tempCharge, batteryMaxCapacity);
		return batteryCharge - batteryHolder;
	}
	/**Drains the battery connected to the camera (assuming it is connected) for a given number of 
minutes. The amount of drain in milliamp-hours (mAh) is the number of minutes times the 
cameras power consumption
	 * @param minutes
	 * takes minutes camera has been running wihtout charge and returns the amount of drain
	 * @return 
	 * returns the amount the battery was drained
	 * @author cadebradford
	 */
	public double drain(double minutes) {
		double holder = batteryCharge;
		double tempDrain = batteryCharge - ((minutes)*(DEFAULT_CAMERA_POWER_CONSUMPTION)*(batteryState/2)*(powerConsumption));
		batteryCharge = Math.max(0, tempDrain);
		totalDrain += Math.abs(batteryCharge - holder);
		return Math.abs(batteryCharge - holder);
	}
	/**Takes the minutes times the charge rate and the charge setting the external charger is set to
	 * @param minutes 
	 * intakes the minutes it has been plugged into the external charger 
	 * @return 
	 * returns the amount the battery was charged
	 * @author cadebradford
	 */
	public double externalCharge(double minutes) {
		double batteryHolder = batteryCharge;
		double tempCharge = batteryCharge + ((minutes)*(CHARGE_RATE)*(chargerSetting)*(batteryState %2));
		batteryCharge = Math.min(tempCharge, batteryMaxCapacity);
		return batteryCharge - batteryHolder;
	}
	/**
	 * Reset the battery monitoring system by setting the total battery drain count back to 0. 
	 * @author cadebradford
	 */
	public void resetBatteryMonitor() {
		totalDrain = 0;
	}
	/**
	 * Get the battery's capacity. 
	 * @return 
	 * returns the batterys max capacity
	 * @author cadebradford
	 */
	public double getBatteryCapacity() {
		return batteryMaxCapacity;
	}
	/**
	 * Get the battery's current charge. 
	 * @return
	 * returns that batterys current charge
	 * @author cadebradford
	 */
	public double getBatteryCharge() {
		return batteryCharge;
	}
	/**
	 * Get the current charge of the camera's battery. 
	 * @return 
	 * returns the cameras charge
	 * @author cadebradford
	 */
	public double getCameraCharge() {
		return (batteryCharge*(batteryState/2)) ;
	}
	/**Get the power consumption of the camera. 
	 * @return 
	 * returns the power consumption rate
	 * @author cadebradford
	 */
	public double getCameraPowerConsumption() {
		return powerConsumption;
	}
	/**
	 * Get the external charger setting. 
	 * @return
	 * retuns the charger setting
	 * @author cadebradford
	 */
	 public int getChargerSetting() {
		 return chargerSetting;
	 }
	 /**Get the total amount of power drained from the battery since the last time the battery monitor 
was started or reset. 
	  * @return 
	  * returns the total drain of the battery
	  * @author cadebradford
	  */
	 
	 public double getTotalDrain() {
		 return totalDrain;
	 }
	 /**
	  * Move the battery to the external charger. Updates any variables as needed to represent the move. 
	  * @author cadebradford
	  */
	 public void moveBatteryExternal() {
		batteryState = 1;
	 }
	 /**
	  * Move the battery to the camera. Updates any variables as needed to represent the move. 
	  * @author cadebradford
	  */
	 public void moveBatteryCamera() {
		 batteryState = 2;
	 }
	 /**
	  * Remove the battery from either the camera or external charger. Updates any variables as needed 
to represent the removal. 
	  * @author cadebradford
	  */
	 public void removeBattery() {
		 batteryState = 0;
	 }
	 /**
	  * Set the power consumption of the camera. 
	  * @author cadebradford
	  */
	 public void setCameraPowerConsumption(double cameraPowerConsumption) {
		 powerConsumption = cameraPowerConsumption; 
	 }
	 
	
	
	
	
	
	
	
	
}
