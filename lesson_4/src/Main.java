import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 300, 250}; // добавил медика, добавил удачливого героя
    public static int[] heroesDamage = {20, 15, 10, 0, 0}; // медик не наносит никакого урона, удачливый тоже не наносит урон
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        medicHeals(); // вызов медика для исцеления,вызвал медика перед боссом, чтобы он успел исцелить героев
        bossAttacks();
        heroesAttack();
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesAttackType[i].equals("Lucky")) {
                    Random random = new Random();
                    int chance = random.nextInt(100); // Случайное число от 0 до 99
                    if (chance < 50) { // 50% шанс уклонения
                        System.out.println("Lucky dodged the attack!");
                        continue; // Lucky уклонился, переходим к следующему герою
                    }
                }
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }
    public static void medicHeals() {
        for (int i = 0; i < heroesHealth.length; i++) {
            // Пропускаем медика
            if (heroesAttackType[i].equals("Medic")) {
                // Проверяем, жив ли медик
                if (heroesHealth[i] <= 0) {
                    return; // Если медик мертв, выходим из метода
                }
                continue; // Если медик жив, продолжаем
            }
            // Исцеляем только героев с менее чем 100 здоровья
            if (heroesHealth[i] < 100) {
                heroesHealth[i] += 30; // Исцеляем на 30 единиц
                System.out.println(heroesAttackType[i] + " healed by Medic for 30 health!");
                break; // Исцеляем только одного героя
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No Defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
