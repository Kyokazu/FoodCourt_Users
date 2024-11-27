FoodCourt Microservices

## Architecture

Hexagonal Architecture

## Dependencies

- Spring Boot
- Lombok
- MapStruct
- Spring Security
- JWT
- Twilio
- MongoDB

## Current Functionalities

- Create Owner
- Create Restaurant
- Create Plate
- Update Plate
- JWT Authorization and Authentication
- Create Employee
- Enable/Unable Plate
- Create Client
- List Restaurant
- List Plate
- Create Order
- List Order
- Notify ready order
- Deliver order
- Cancel order
- Client's listing Order Status change timeline
- Order and employee Ranking

## Changelog

v1.0.2

- Modified mail existence validation.
- Added Qualifier for client use case.

v1.0.3

- Modified restaurant controller for adding listing restaurants route.
- Added Qualifier for list restaurant use case.

v1.0.4

- Modified plate controller for adding listing plates route.
- Added Qualifier for list plate use case.

v1.0.5

- Added Order management (model,DTO, entity, handler, repository, service, controller, adapter, UseCase).
- Added Qualifier for creating order.

v1.0.6

- Added order listing, filtering by status and number of records to show
- Added Qualifier for listing order
- Modified employee creation for saving employee's id on restaurant data.

v1.0.7

- Added order assignment to an employee from the restaurant which took the order.
- Added Qualifier for assign order

v1.0.8

- Added Twilio dependency for SMS/Whatsapp messaging services
- Added Order notification service
- Added Qualifier notifyReadyOrder
- Modified ORDER table for storing the generated security PIN for order
- Added the twilio configurations and services.

v1.0.9

- Added DeliverOrderUseCase for deliver the order to the client
- Added Qualifier deliverOrder

v1.1.0

- Added CancelOrderUseCase for cancelling pending orders
- Code was refactor for deleting generic implementations
- Added Qualifier cancelOrder

v1.1.1

- Added StatusChangeUseCase listing status change of a client's orders
- Modified ALL orderUseCases for inserting into MongoDB collection the date of status change
- Added Qualifier statusChange
- Added TrazabilityController for methods to interact with time/log metrics.
- Added connection to MongoDB for Log history of orders.
- Added order and employee ranking

