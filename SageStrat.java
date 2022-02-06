package peterbattlecode;
import battlecode.common.*;
import java.util.Random;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
strictfp class SageStrat {
    static Direction finaldir = null;
    static boolean done = false;
    static void runSage(RobotController rc) throws GameActionException {
        // Try to attack someone
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        Team friend = rc.getTeam();
        RobotInfo[] friends = rc.senseNearbyRobots(radius,friend);
        MapLocation me = rc.getLocation();
        if (enemies.length > 0) {
            MapLocation toAttack = enemies[0].location;
            if (rc.canAttack(toAttack)) {
                if (rc.canEnvision(AnomalyType.CHARGE) && enemies.length > 4){
                    rc.envision(AnomalyType.CHARGE);
                }
                else{
                rc.attack(toAttack);
            }
            }
            else{
                Pathing.walkTowards(rc, toAttack);
            }
        }
        else{
            if (rc.getRoundNum() > 1500){
                finaldir = me.directionTo(Communication.getClosestEnemy(rc));
                if (rc.canMove(finaldir)) {
                    rc.move(finaldir);
                }
                else{
                Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
                if (rc.canMove(dir)) {
                    rc.move(dir);
                    }
                }
                }
            else{
            Pathing.walkTowards(rc, Communication.getClosestEnemy(rc));
            Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
        }
    }
    }
}
