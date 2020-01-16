#About the App
A simulation delivery system for a group of shops:

*Assume there are 3 kind of products: 
1. food (warm and frozen meals), 2. flower bouquets 3. gift chocolate boxes.

*Some of the food products are warm and require to be kept warm during transportation, and
some of the foods are frozen food and has to be cooled. Some of the vans have freezer on
board so that frozen food should be assigned only to them if the delivery distance is larger
than 2 miles.

*If the customer has birthday and orders any kind of products, we want to deliver as a birthday
present a chocolate boxes and a flower bouquet.

*In the event of high traffic in the city (like rush hours), all frozen food should be assigned to
the vans with freezers.

*when a store gets an order, it looks for drivers within 5 miles from the store.



#Outline of the project:

Create 10 customers
Create Products
Create 5 Shops
	assign 5 products with all types covered
Create 10 Drivers
	random distance to stores
	status == available
Initialize unassigned order list to empty

for time == 1, until 10 orders have completed
	randomly create 20 orders when t=1
		pick a store...
		check if customer birthday -> add chocolate and flowers (assume all stores have them)
		add to unassigned order list
	
	
		
	go through unassigned order list and check if we can assign orders 
	
		check order : 
			find cloeast driver:
				Send order notification to all drivers. when driver receives order notification, driver send back status information
		
				check if driver can fullfil order (check if rush hour, frozen->freezer drivers)
					if yes, assign order to driver
						remove driver from unassigned drivers list
						update driver status to drive to store
						update distance to destination to driver.distanceToStores[order.shop]
						update current order
						remove order from unassigned orders list
						
	for each driver, update:
		if driving to store:
			distance to destination--
			if now zero:
				update status to driving to customer
				update distance to destination = order.customer.distanceToStores[order.store]
		else if driving to customer:
			dist to dest--
			if now zero:
				add driver back to unassigned drivers??
				update status to available
				distanceToStores = currentOrder.customer.distanceToStores
				currentOrder = null
				

# DeliveryApp
