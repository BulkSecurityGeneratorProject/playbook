# playbook
Proyecto de pruebas para Spring y otras tecnologias

## Consideraciones
1. Se usará un criterio de paquetes por características o entidades en la parte back y por capa (controladores) en la parte web.
2. Se seguirán las lineas maestras para inyección de dependencias, priorizando la inyección por constructor, dejando la inyección por setters para campos opcionales, y evitando la inyección por atributos (complica las pruebas por la inyección de mocks y hace la clase muy dependiente y acoplada a Spring, además de permitir muchas dependencias que reflejan un incumplimiento del principio de responsabilidad unica, que el caso de inyección por constructor llamaría enseguida la atención).
