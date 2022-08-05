import com.bloomberg.samples.easymsx.Broker;
import com.bloomberg.samples.easymsx.BrokerStrategy;
import com.bloomberg.samples.easymsx.BrokerStrategyParameter;
import com.bloomberg.samples.easymsx.EasyMSX;
import com.bloomberg.samples.easymsx.EasyMSX.Environment;
import com.bloomberg.samples.easymsx.FieldChange;
import com.bloomberg.samples.easymsx.Log;
import com.bloomberg.samples.easymsx.Notification;
import com.bloomberg.samples.easymsx.Notification.NotificationCategory;
import com.bloomberg.samples.easymsx.Notification.NotificationType;
import com.bloomberg.samples.easymsx.NotificationHandler;
import com.bloomberg.samples.easymsx.Order;
import com.bloomberg.samples.easymsx.Route;
import com.bloomberg.samples.easymsx.Team;


public class EasyMSXAPISample implements NotificationHandler {

	EasyMSX emsx;
	
    public static void main(String[] args) throws java.lang.Exception
    {
        System.out.println("Bloomberg - EasyMSX Example - EasyMSXAPISample");

        EasyMSXAPISample example = new EasyMSXAPISample();
        example.run(args);
        
        System.in.read();    

    }

	private void run(String[] args) throws Exception {
	
		Log.logLevel = Log.LogLevels.NONE;

		System.out.println("Initializing EMSXAPI ");

		// Uncomment the appropriate constructor call
		//emsx = new EasyMSX(Environment.BETA,"RCAM_API");
		emsx = new EasyMSX(Environment.BETA);
		//emsx = new EasyMSX(Environment.BETA, "bpipe-ny-beta.bdns.bloomberg.com", 8194,"CORP\\rclegg2","1.1.1.1");

		
		System.out.println("EMSXAPI initialized OK");
		
		emsx.orders.addNotificationHandler(this);
		emsx.routes.addNotificationHandler(this);
		
		for(Team t: emsx.teams) {
			System.out.println("Team: " + t.name);
		}
	
		for(Broker b: emsx.brokers) {
			System.out.println("Broker: " + b.name);
			
			System.out.println("\tAsset Class: " + b.assetClass.toString());
			
			for(BrokerStrategy s: b.strategies) {
				System.out.println("\tStrategy: " + s.name);
				
				for(BrokerStrategyParameter p: s.parameters) {
					System.out.println("\t\tParameter: " + p.name);
				}
			}
		}
		
		System.out.println("Existing Orders:");
		
		for(Order o: emsx.orders) {
			System.out.println("\tSequence: " + o.field("EMSX_SEQUENCE").value() + "\tStatus: " + o.field("EMSX_STATUS").value() + "\t Ticker: " + o.field("EMSX_TICKER").value() + "\t Amount: " + o.field("EMSX_AMOUNT").value());
		}

		System.out.println("Existing Routes:");
		
		for(Route r: emsx.routes) {
			System.out.println("\tSequence: " + r.field("EMSX_SEQUENCE").value() + "\tID: " + r.field("EMSX_ROUTE_ID").value() + "\tStatus: " + r.field("EMSX_STATUS").value()  + "\t Amount: " + r.field("EMSX_AMOUNT").value());
		}
	}

	@Override
	public void processNotification(Notification notification) {

		if(notification.category==NotificationCategory.ORDER) {
			if(notification.type.equals(NotificationType.ERROR)) {
				System.out.println("Order Notification [" + notification.category.toString() + "|" + notification.type.toString() + "] "  + " Error Code:" + notification.errorCode() + "\t" + notification.errorMessage());
			} else {
				System.out.println("Order Notification [" + notification.category.toString() + "|" + notification.type.toString() + "] "  + " Order: " + notification.getOrder().field("EMSX_SEQUENCE").value() + " : No. of affected fields: " + notification.getFieldChanges().size());
				notification.consume = true;
				for(FieldChange fc: notification.getFieldChanges()) {
					System.out.println("\t\tChange: " + fc.field.name() + "\tOld Value: " + fc.oldValue + "\tNew Value: " + fc.newValue);
				}
			}
		} 
		else if(notification.category==NotificationCategory.ROUTE) {
			if(notification.type.equals(NotificationType.ERROR)) {
				System.out.println("Route Notification [" + notification.category.toString() + "|" + notification.type.toString() + "] "  + " Error Code:" + notification.errorCode() + "\t" + notification.errorMessage());
			} else {
				System.out.println("Route Notification [" + notification.category.toString() + "|" + notification.type.toString() + "] "  + " Route: " + notification.getRoute().field("EMSX_SEQUENCE").value() + "." + notification.getRoute().field("EMSX_ROUTE_ID").value() + " : No. of affected fields: " + notification.getFieldChanges().size());
				notification.consume = true;
				for(FieldChange fc: notification.getFieldChanges()) {
					//System.out.println("\t\tChange: " + fc.field.name() + "\tOld Value: " + fc.oldValue + "\tNew Value: " + fc.newValue);
				}
			}
		}
	}

}
