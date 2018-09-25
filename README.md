# playbook
Proyecto de pruebas para Spring y otras tecnologias

## Consideraciones
1. Se usará un criterio de paquetes por características o entidades en la parte back y por capa (controladores) en la parte web.
2. Se seguirán las lineas maestras para inyección de dependencias, priorizando la inyección por constructor, dejando la inyección por setters para campos opcionales, y evitando la inyección por atributos, ya que:
   - Complica las pruebas, ya que las dependencias las gestiona el contenedor de dependias de Spring y no nosotros, por lo que no es            sencillo inyectar el mock o el objeto que queramos.
   - Hace la clase muy dependiente y acoplada a Spring, ya que la responsabilidad de gestionar las dependencias ya no es de la clase sino        del contenedor de Spring.
   - Permite tener fácilmente muchas dependencias que reflejan un incumplimiento del principio de responsabilidad unica, que el caso de          inyección por constructor llamaría enseguida la atención
   - Hace que la clase no sea responsable de sus dependencias, ni tampoco las refleja al exterior mediante su constructor o setters.
   - No se pueden asignar dependencias a campos final, por lo que la clase no puede ser inmutable.
