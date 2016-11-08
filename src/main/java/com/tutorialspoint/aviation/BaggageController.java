package com.tutorialspoint.aviation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorialspoint.model.BaggageItem;
import com.tutorialspoint.model.GenericResult;
import com.tutorialspoint.model.Product;

@Controller
public class BaggageController {
	   
		private List<BaggageItem> baggageItems;
		private int maxBaggageId = 1;
	    public BaggageController() {
			super();
			baggageItems = new ArrayList<BaggageItem>();
			
			BaggageItem flight1 = new BaggageItem(""+maxBaggageId++, ""+100001, "40", "910", "EWR", "HKG", "01/01/2017 20.40", "CX");
			baggageItems.add(flight1);
			
			BaggageItem flight2 = new BaggageItem(""+maxBaggageId++, ""+100012, "30", "870", "SFO", "PVG", "11/01/2017 22.10", "CX");
			baggageItems.add(flight2);
			
			BaggageItem flight3 = new BaggageItem(""+maxBaggageId++, ""+100003, "32", "489", "LAX", "PEK", "01/01/2017 12.25", "CX");
			baggageItems.add(flight3);
		}



	@RequestMapping(value="/baggage/intl/item", method=RequestMethod.POST, produces="application/json", consumes="application/json") 
    public @ResponseBody BaggageItem postBaggageItem(@RequestBody BaggageItem newItem) {
		BaggageItem item = new BaggageItem(""+maxBaggageId++, newItem.getPassengerId(), newItem.getWeight(), newItem.getFlightNumber(), newItem.getFrom(), newItem.getTo(), newItem.getDepartureDateAndTime(), newItem.getAirline());
											
		baggageItems.add(item);
		
    	return item;
    }
	
	@RequestMapping(value="/baggage/intl/item/{id}", method=RequestMethod.DELETE, produces="application/json") 
    public @ResponseBody GenericResult deleteBaggageItem(@PathVariable("id") String id) {
        
		BaggageItem foundItem = getBaggageItem(id);
    	if(foundItem != null){
    		baggageItems.remove(foundItem);
    		return new GenericResult("BaggageItem "+id+" successfully deleted");
    	}
    	
    	return new GenericResult("BaggageItem "+id+" NOT FOUND");
    }
	
	

	@RequestMapping(value="/baggage/intl/item/{id}", method=RequestMethod.PUT, consumes="application/json") 
    public @ResponseBody GenericResult updateBaggageItem(@RequestBody BaggageItem newItem, @PathVariable("id") String id) {
        
		BaggageItem foundItem = getBaggageItem(id);
    	if(foundItem != null){
       		foundItem.setPassengerId(newItem.getPassengerId());
       		foundItem.setWeight(newItem.getWeight());
       		foundItem.setFlightNumber(newItem.getFlightNumber());
       		foundItem.setFrom(newItem.getFrom());
       		foundItem.setTo(newItem.getTo());
       		foundItem.setDepartureDateAndTime(newItem.getDepartureDateAndTime());
       		foundItem.setAirline(newItem.getAirline());
    		return new GenericResult("BaggageItem "+id+" successfully updated");
    	}
    	
    	return new GenericResult("BaggageItem "+id+" NOT FOUND");
    }
	
	
	@RequestMapping(value="/baggage/intl/items", method=RequestMethod.GET, produces="application/json") 
    public @ResponseBody List<BaggageItem> getbaggageItems() {
		
    	return baggageItems;
    	
    }
	
	@RequestMapping(value="/baggage/intl/item/{id}", method=RequestMethod.GET, produces="application/json") 
    public @ResponseBody BaggageItem getbaggageItems(@PathVariable("id") String id) {
		BaggageItem foundItem = getBaggageItem(id);
    	if(foundItem != null){
    		return foundItem;
    	}	
    	return new BaggageItem();
    	
    }
	
	
	//******************************************* Private Methods *******************************************
	
	private BaggageItem getBaggageItem(String id){
		BaggageItem ret = null;
		for (BaggageItem item: baggageItems){
			if (item.getId().equals(id)){
				ret = item;
				break;
			}
		}
		return ret;
	}
	
}
