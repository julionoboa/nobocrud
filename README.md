# NoboCRUD

This project is a CRUD that allows managing products and sales. It uses Spring Boot and MySQL for the database.

## Database Configuration

Before running the project, make sure to modify the `application.properties` file with the corresponding data for your MySQL database:

spring.datasource.url=jdbc:mysql://localhost:3306/nobocrud
spring.datasource.username=root
spring.datasource.password=root


## Installation

1. Clone this repository: `git clone https://github.com/julionoboa/nobocrud.git`
2. Open the project in your favorite IDE.
3. Modify the `application.properties` file with your MySQL database data.
4. Run the application.

## Usage

Once the application is up and running, you can access the following endpoints:

### Products

- `POST /rest/addNewProduct`: Add a new product.
- `POST /rest/addNewProducts`: Add multiple products.
- `GET /rest/findAllProducts`: Get all products.
- `GET /rest/findProductById/{id}`: Find a product by its ID.
- `GET /rest/findProductByName/{name}`: Find products by name.
- `PUT /rest/updateProduct`: Update a product.
- `PUT /rest/updateProductName/{id}/{name}`: Update a product's name.
- `PUT /rest/updateProductPrice/{id}/{price}`: Update a product's price.
- `PUT /rest/updateProductStock/{id}/{stock}`: Update a product's stock.
- `DELETE /rest/deleteProductById/{id}`: Delete a product by its ID.

### Sales

- `POST /rest/addNewSale/{productName}/{quantity}/{clientName}`: Add a new sale.
- `POST /rest/addNewSales/{clientName}`: Add multiple sales.
- `GET /rest/findAllSales`: Get all sales.
- `PUT /rest/updateSale/{id}/{clientName}/{quantity}`: Update a sale.
- `DELETE /rest/deleteSale/{id}`: Delete a sale by its ID.
- `GET /rest/findSalesByClientName/{clientName}`: Find sales by client name.
- `GET /rest/findSaleById/{id}`: Find a sale by its ID.

Refer to the API documentation for more details on each endpoint.

## Contribution

If you'd like to contribute to this project, follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes and commit them (`git commit -am 'Add new feature'`).
4. Push your changes to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
