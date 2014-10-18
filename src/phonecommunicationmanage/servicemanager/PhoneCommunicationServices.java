package phonecommunicationmanage.servicemanager;

import android.content.Context;
import android.content.Intent;

public class PhoneCommunicationServices {
	public static final String SERVICE_MANAGER_SERVICE_INTENT_ACTION = "phonecommunicationmanage.intent.action.ServiceManagerService";
	public static final String SERVICE_MANAGER_SERVICE_CLASS_NAME = "phonecommunicationmanage.servicemanager.ServiceManager";
	
	public static final String TIME_TICK_SERVICE_INTENT_ACTION = "phonecommunicationmanage.intent.action.TimeTickServer";
	public static final String TIME_TICK_SERVICE_CLASS_NAME = "phonecommunicationmanage.servicemanager.TimeTickServer";
	
	
	
	public static final String SMS_SERVICE_INTENT_ACTION = "phonecommunicationmanage.intent.action.SMSManageService";
	public static final String SMS_SERVICE_CLASS_NAME = "phonecommunicationmanage.servicemanager.smsservice.SMSManageService";
	
	public static final String PHONE_SERVICE_INTENT_ACTION = "phonecommunicationmanage.intent.action.PhoneManageService";
	public static final String PHONE_SERVICE_CLASS_NAME = "phonecommunicationmanage.servicemanager.phoneservice.PhoneManageService";
	
	
	
	public static final String[] s_vec_ServicesClassName = {
		SMS_SERVICE_CLASS_NAME,
		PHONE_SERVICE_CLASS_NAME,
	};
	
	public static Intent startSeparateService(Context context, String intent_action) {
		Intent service_intent = new Intent(intent_action);
		context.startService(service_intent);
		return service_intent;
	}
	
	public static boolean stopSeparateService(Context context, Intent service_intent) {
		return context.stopService(service_intent);
	}
}
