package controlador.navegacion;

import controlador.gestorHabilidades.Habilidad;
import infraestructura.*;

/**
 * Clase que hereda de Habilidad y define las caracteristicas 
 * propias de esta habilidad e implementa su bucle de ejecucion 
 * y la condicion del evento de finalizacion.
 * 
 * @author Jose Luis Diaz Cebrian
 * @version 1.0
 * 
 */
public class Avanzar extends Habilidad{
	
	/**
	 * Indica la velocidad lineal de las ruedas que permiten el avance en linea
	 * recta del robot.
	 */
	private final double VELOCIDAD;
	
	
	/**
	 * Inicializa los atributos de la habilidad segun resultados obtenidos en experimentaciones
	 * previas de robotica.
	 * 
	 * @param robot El robot del sistema que hace uso de la habilidad
	 * @param sim A "true" si se utiliza la arquitectura sobre simulador
	 * 
	 */
	public Avanzar(Robot robot, boolean sim){
		super("Avanzar", robot, sim);
		if(sim){
			this.VELOCIDAD = 30.0;
		} else{
			this.VELOCIDAD = 60.0;
		}
	}
	
	
	/**
	 * Devuelve el valor actual de la velocidad asignada a las ruedas del robot.
	 * 
	 * @return La velocidad lineal del robot
	 * 
	 */
	public double getVelocidad(){
		return this.VELOCIDAD;
	}
	
	/**
	 * @see Habilidad#ejecutar()
	 */
	public void ejecutar(){
		int[] ruedas = new int[]{(int)this.VELOCIDAD, (int)this.VELOCIDAD}; //Izda-Dcha
		Motor m = (Motor) super.getRobot().getActuador("motor");
		m.setRuedas(ruedas[0], ruedas[1]);	
	}
	
	/**
	 * @see Habilidad#comprobarExito()
	 */
	public boolean comprobarExito(){
		boolean exito = false;
		
		if(super.getSim())
			exito = exitoSimulador();
		else
			exito = exitoRobotReal();
		
		return exito;
	}
	
	/**
	 * Comprueba la condicion de exito de la habilidad teniendo en cuenta los 
	 * umbrales y pequenas diferencias en el algoritmo para el simulador.
	 * 
	 * @return "true" si se ha ejecutado con exito la habilidad, "false" en caso contrario
	 */
	public boolean exitoSimulador(){
		
		boolean exito = false;
		
		Sonar s = (Sonar) super.getRobot().getSensor("sonar");
		double ds_fr = Math.max(Math.max(s.getValorSonar(0), s.getValorSonar(1)), Math.max(s.getValorSonar(6), s.getValorSonar(7)));
		if(ds_fr>100.0)
			exito = true;
		
		return exito;
	}
	
	/**
	 * Comprueba la condicion de exito de la habilidad teniendo en cuenta los 
	 * umbrales y pequenas diferencias en el algoritmo para el robot real sobre ARIA.
	 * 
	 * @return "true" si se ha ejecutado con exito la habilidad, "false" en caso contrario
	 */
	public boolean exitoRobotReal(){
		boolean exito = false;
		
		Sonar s = (Sonar) super.getRobot().getSensor("sonar");
		double ds_fr = Math.min(Math.min(s.getValorSonar(2), s.getValorSonar(3)), Math.min(s.getValorSonar(4), s.getValorSonar(5)));
		if(ds_fr<300.0)
			exito = true;
		
		return exito;
	}
	
	
}
