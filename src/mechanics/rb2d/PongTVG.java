package mechanics.rb2d;

import java.awt.event.KeyEvent;

import InclinePlain.Ball;
import de.physolator.usr.PhysicalSystem;
import de.physolator.usr.Recorder;
import de.physolator.usr.Structure;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.tvg.events.EventHandler;
import de.physolator.usr.tvg.events.EventHandlerAdvanced;
import de.physolator.usr.tvg.events.KeyEventTable;
import mechanics.tvg.MechanicsTVG;

public class PongTVG extends MechanicsTVG {
	private RigidBodiesPS pongPS;
	boolean birdshot = false;
	double chargingX = 0;
	double chargingY = 0;
	int value = 0;
	long startTime = System.currentTimeMillis();
	long lastSecond = 0;
	


	
	public PongTVG(PhysicalSystem ps, Structure structure, Recorder recorder) {
		super(ps, structure, recorder);
		pongPS = (RigidBodiesPS) ps;
		geometry.setUserArea(-12, 12, -2, 15);
//		pongPS.choice = 0;
		
	}

	@Override
	public void initKeys(KeyEventTable keyEventTable) {
		// TODO Auto-generated method stub
		super.initKeys(keyEventTable);
		keyEventTable.addEntry(KeyEvent.VK_R, new EventHandler() {
			@Override
			public void handleKeyEvent() {
			//	System.out.println("r wurde gedr�ckt");
				pongPS.z�hler--;
			}

			@Override
			public void handleKeyReleaseEvent() {
				System.out.println("r wurde losgelassen");
			}
		});
		keyEventTable.addEntry(KeyEvent.VK_T, new EventHandler() {
			@Override
			public void handleKeyEvent() {
			//	System.out.println("t wurde gedr�ckt");
			//	pongPS.z�hler++;
			}

			@Override
			public void handleKeyReleaseEvent() {
				System.out.println("t wurde losgelassen");
			}
		});
		keyEventTable.addEntry(KeyEvent.VK_F, new EventHandlerAdvanced() {
			private double startPosition;

			@Override
			public void handleKeyEvent() {
		//		System.out.println("f wurde gedr�ckt");
				startPosition = pongPS.position;
				
			}

			@Override
			public void handleAfterKeyEventAction(long millis) {
				 if (pongPS.choice == 0) {
				pongPS.position = startPosition - millis * 0.002;
				pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ; 
				if (pongPS.rigidBodies[0].r.x <= -6.9) {
					pongPS.rigidBodies[0].r.x = -6.9;
				}
		//		pongPS.balls[0].r.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ; 
			}
				 else if (pongPS.choice == 1)	{ 
					 pongPS.position = startPosition - millis * 0.002;
						pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ;
						//On collision: 
						//pongPS.balls[0].v.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ;
			}
				 else if (pongPS.choice == 2)	{ 
					 pongPS.position = startPosition - millis * 0.002;
						pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ; 
			}
				 
				 else if (chargingX >= -6 && birdshot == false)	{ 
					 pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x- millis * 0.002, pongPS.rigidBodies[0].r.y) ; 	
					 chargingX = pongPS.rigidBodies[0].r.x;
			}
				 
				 }
		});
		keyEventTable.addEntry(KeyEvent.VK_G, new EventHandlerAdvanced() {
			private double startPosition;

			@Override
			public void handleKeyEvent() {
				System.out.println("G wurde gedr�ckt");
			startPosition = pongPS.position;
				
			}

			@Override
			public void handleAfterKeyEventAction(long millis) {
				 if (pongPS.choice != 3) {
						pongPS.position = startPosition - millis * 0.002;

					pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x+ millis * 0.002, pongPS.rigidBodies[0].r.y) ;
						if (pongPS.rigidBodies[0].r.x >= 6.9) {
						pongPS.rigidBodies[0].r.x = 6.9;
						}
						
					}
				 else	 if ( birdshot == false)	{ 
					 pongPS.rigidBodies[0].v.set(pongPS.rigidBodies[0].v.x - chargingX , pongPS.rigidBodies[0].r.y - chargingY -3.5) ; 
					 birdshot = true;
				
			}
						 }
				});
		keyEventTable.addEntry(KeyEvent.VK_V, new EventHandlerAdvanced() {
			private double startPosition;

			@Override
			public void handleKeyEvent() {
				System.out.println("V wurde gedr�ckt");
			startPosition = pongPS.position;
				
			}

			@Override
			public void handleAfterKeyEventAction(long millis) {
				 if (pongPS.choice != 3) {
					}
				 else if (chargingY >= 0 && birdshot == false)	{ 
					 pongPS.rigidBodies[0].r.set(pongPS.rigidBodies[0].r.x, pongPS.rigidBodies[0].r.y -  millis * 0.002) ; 	
					 chargingY = pongPS.rigidBodies[0].r.y;
				
			}
						 }
				});
	}
	
	@Override
	public void paint() {
		if (pongPS.choice == 0) {
		
		if (pongPS.rigidBodies[0].collided == true && pongPS.rigidBodies[0].colID == 2) {
			pongPS.z�hler++;
		}
		pongPS.rigidBodies[0].collided = false;
		super.paint();
		style.useUCS = false; 
		long elapsedTime = System.currentTimeMillis() - startTime;
		long elapsedSeconds = elapsedTime / 1000;
	if (pongPS.rigidBodies[1].r.y >= -1) {	
		drawText(70, geometry.maxYPX + 30, "Bounces: " + pongPS.z�hler + "           " + "Time: " + elapsedSeconds + " Seconds");
		lastSecond = elapsedSeconds;
	}
	else {
	drawText(70, geometry.maxYPX + 30, "Bounces: " + pongPS.z�hler + "           " + "Time: " + lastSecond + " Seconds");
	}
		}
	
		
	else if (pongPS.choice == 1)	{
		if (pongPS.rigidBodies[0].collided == true) {
			pongPS.rigidBodies[0].r.x = -20;	
		}
		else if (pongPS.rigidBodies[1].collided == true) {
			pongPS.rigidBodies[1].r.x = -30;	
		}
		else if (pongPS.rigidBodies[2].collided == true) {
			pongPS.rigidBodies[2].r.x = -40;	
		}
		else if (pongPS.rigidBodies[3].collided == true) {
			pongPS.rigidBodies[3].r.x = -50;	
		}
		else if (pongPS.rigidBodies[4].collided == true) {
			pongPS.rigidBodies[4].r.x = -60;	
		}
		else if (pongPS.rigidBodies[5].collided == true) {
			pongPS.rigidBodies[5].r.x = -70;	
		}
		else if (pongPS.rigidBodies[6].collided == true) {
			pongPS.rigidBodies[6].r.x = -80;	
		}
		else if (pongPS.rigidBodies[7].collided == true) {
			pongPS.rigidBodies[7].r.x = -90;	
		}
	}
}
 	
	
	

}
