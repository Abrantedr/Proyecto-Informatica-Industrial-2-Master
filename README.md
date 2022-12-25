# Proyecto-Informatica-Industrial-2-Master

## Introducción

Este proyecto final de la asignatura de Informática Industrial, impartida en
2022 por Cándido Caballero Gil en el Máster de Ingeniería Industrial (ULL), 
consiste en el diseño y programación de una aplicación de visualización de datos 
de generación y demanda energética en Canarias.

> El objetivo de la práctica que se plantea es el diseño y programación de un 
> sistema de monitorización y control de las señales procedentes del operador del 
> sistema eléctrico en el mercado eléctrico español, REE. Este aplicativo será 
> programado en Java y para ello se accederán a los datos que publica REE en su 
> página web.

![](https://i.imgur.com/l5vi7FJ.png)

## Programa

### Características

+ Interfaz gráfica básica e intuitiva: un panel de visualización, 8 tipos de 
series temporales, un rango de fechas y dos botones para seleccionar un periodo 
y descargar una imagen.

+ Datos de la demanda y generación energética en Canarias, obtenidos a través de 
la API de Red Eléctrica España (https://demanda.ree.es/visiona/canarias/canarias/). 
Puedes comparar los datos directamente desde aquí.

+ Superposición de series a través de botones. Demanda total, generación con 
motores diésel, generación con turbinas de gas, generación eólica, generación 
en ciclos combinados, generación en ciclo de vapor, generación fotovoltaica y 
generación hidráulica.

+ Selección de fechas. Por defecto, se selecciona la fecha actual. Si existen
datos en las fechas seleccionadas, se atacará a la API para obtener las series.

+ Personalización de las gráficas. Con un _click_ derecho, podemos cambiar las
propiedades del gráfico, hacer _zoom_, imprimir la imagen, cambiar los colores,
títulos y ejes, etc. Una herramienta versátil y rápida.

### ¿Cómo funciona?

La aplicación se apoya en dos clases, `Gui` y `Api`. La clase `Gui` genera toda
la interfaz gráfica y define el comportamiento de cada elemento que interactúa
con el usuario. Por otro lado, la clase `Api` es la que ataca a la API de Red
Eléctrica España. A partir de la siguiente URL

```Java
public static final String getUrl(final String date) {
        return "https://demanda.ree.es/WSvisionaMovilesCanariasRest/" +
                "resources/demandaGeneracionCanarias?" +
                "callback=angular.callbacks._2&curva=CANARIAS&" +
                "fecha=" + date;
    }
```

se obtiene un `.json` con los datos de demanda y generación en Canarias para una
fecha `date` dada. Posteriormente, este archivo se guarda en una ruta local y
se segmenta (se _parsea_) para extraer los datos de cada serie temporal. `Gui`
se encarga de hacer llamadas a la API con las fechas adecuadas y de representar
los datos en un panel gráfico.

### ¿Qué se necesita para ejecutarlo?

Para ejecutar el programa necesitaremos Java _Swing_ y algunas librerías que
se adjuntan en el repositorio:

+ `jfreechart` para la representación de gráficos.
+ `jcommon` para algunas herramientas y útiles.
+ `javax.json` para _parsear_ archivos `json`.
+ `datepicker4j` para añadir un calendario interactivo.

### Mejoras que nunca implementaré

- [ ] Posibilidad de ver una barra de progreso que indique las llamadas que
faltan por realizar cuando se trata de una petición larga.
- [ ] Refactorizar el programa debidamente, utilizando la estructura de la API
actual.
- [ ] Capacidad de detener el proceso para una llamada muy larga (tomar datos de
un año entero).
- [ ] Funcionalidad para exportar datos a excel o json.
- [ ] Funcionalidad para cambiar la zona de demanda y generación.

### Casos de uso

Cuando lo abrimos por primera vez, se obtienen los datos de demanda total para
el día de hoy.

![](https://imgur.com/iABKfeU.png)

Por ejemplo, el día 25 de diciembre de 2022 tuvo un valle de unos 700 MW a 
las 5 de la mañana. Podemos seleccionar las series temporales que queremos 
visualizar al hacer _click_ en los botones.

![](https://imgur.com/ENJsjaC.png)

Hemos elegido visualizar la generación en ciclo combinado y diésel de este día. 
Se ve que la curva de generación con ciclo combinado es un poco menos del doble 
que la generación con motores diésel, alcanzando valores máximos sobre las 8 de
la noche. También podemos visualizar todo de golpe

![](https://imgur.com/U22sSZq.png)

Sin embargo, esto a veces no es muy recomendable, ya que algunos valores son
muy bajos en comparación con otros (como por ejemplo, la curva de demanda total
frente a la generación con turbina de gas). 

Vamos a elegir un rango de fechas anterior, en lugar de ver la de un solo día. Si
hacemos _click_ en el campo de fecha que pone _Desde:_ o _Hasta:_, nos saldrá un 
calendario contextual con el que podremos elegir la fecha.

![](https://imgur.com/uHi40gC.png)

También podemos escribir directamente en este campo para elegir una fecha. El
formato correcto es del tipo "YYYY-MM-dd". Si cometemos un error y ponemos una 
fecha errónea, no se podrá visualizar la gráfica. Veamos los datos de generación 
eólica y fotovoltaica desde el 21-12-2022 hasta el 23-12-2022. Si pulsamos en 
_Seleccionar_, la aplicación realizará varias llamadas a la API de Red Eléctrica 
España para las fechas seleccionadas.

![](https://imgur.com/ILuZhJo.png)

El título de la gráfica se ha actualizado para reflejar que estamos visualizando
un rango de fechas. Ahora, hagamos _zoom_ en el pico máximo de generación 
fotovoltaica del día 21 de diciembre (_click_ izquierdo o derecho en la gráfica, 
hacia abajo a la derecha)

![](https://imgur.com/Jpohz7d.png)

![](https://imgur.com/QQDZEFP.png)

Se observa que a la una de la tarde se produce un pico de generación de casi
135 MW en Canarias. La eólica se queda por debajo con un valor medio en torno a 
los 105 MW. Si hacemos _click_ derecho sobre la gráfica, podemos acceder a un 
menú que nos permite cambiar los títulos, colores de las series, fondo de la 
gráfica, copiar o guardar la gráfica en formato `png`, hacer _zoom_ o elegir 
una escala automática. Vamos a _resetear_ los ejes.

![](https://imgur.com/7Q0lK0P.png)

Por último, el botón de _Descargar_ genera el gráfico que estamos visualizando
en formato `png` (la primera imagen de este `README` ha sido obtenida así). Es 
un _shortcut_ del menú pertinente que se ve con el _click_ derecho. La imagen se 
guarda en el directorio desde el que se ejecuta la aplicación.