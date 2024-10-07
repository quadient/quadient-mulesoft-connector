# Quadient Evolve Extension

Quadient Evolve Extension is a MuleSoft Certified Connector. It provides a solution for integrating the functionality of Quadient Evolve into your business applications. You can easily manage and customize your communications with Quadient Evolve.

Quadient Evolve Extension provides the following features for MuleSoft-based enterprise solutions:

# Available Features in  [1.0.0](https://github.com/quadient/quadient-mulesoft-connector/tree/release/1.0.0)

### On-demand application

* On-demand job - Starts and receives results of on-demand jobs in Quadient Evolve. Includes PDF generation and downloading.

### Batch application
* Create a new batch job - Creates a new batch job in Quadient Evolve.
* Get batch job status - Gets the status of a batch jobs in Quadient Evolve.
* Create working folder - Creates a working folder in Quadient Evolve.


### Content Author application
* Get templates from Content Author - Gets templates from Content Author in Quadient Evolve.

### Front Office application
* Create Ticket - Creates a ticket via Front Office in Quadient Evolve.

# Requirements

| Application/Service | Version      |
|---------------------|--------------|
| Mule Runtime        | 4.6 or later |
| Java                | 1.8, 11, 17  |

# Prerequisites
In order to use this module, a Quadient Evolve API key is required. Please contact Quadient directly at [https://www.quadient.com/en/contact-us](https://www.quadient.com/en/contact-us).

# How to use
Add this dependency to your application's pom.xml

```
<groupId>com.quadient.connectors</groupId>
<artifactId>mule-quadient-evolve-connector</artifactId>
<version>1.0.0</version>
<classifier>mule-plugin</classifier>
```
