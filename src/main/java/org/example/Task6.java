import java.util.Locale;
import java.util.Scanner;

public class Calculate {

    static class Result {
        double sn;    // сумма n слагаемых
        double se;    // сумма |a_k| > e
        double se10;  // сумма |a_k| > e/10
        double f;     // точное значение функции
        int n, ne, ne10;
    }

    // рекуррентная формула: a_k через a_(k-1)
    public static double nextTerm(double prev, double x, int k) {
        return prev * (-(k + 2.0) / k * x);
    }

    public static Result calculate(double x, int n, double e) {
        Result r = new Result();

        r.f = 1.0 / Math.pow(1 + x, 3);

        double a = 1.0; // a0
        r.sn = r.se = r.se10 = 0.0;
        r.n = r.ne = r.ne10 = 0;

        for (int k = 1; k <= n || Math.abs(a) > e / 10; k++) {

            if (k <= n) {
                r.sn += a;
                r.n++;
            }

            if (Math.abs(a) > e) {
                r.se += a;
                r.ne++;
            }

            if (Math.abs(a) > e / 10) {
                r.se10 += a;
                r.ne10++;
            }

            a = nextTerm(a, x, k);
        }

        return r;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите x (|x| < 1): ");
        double x = sc.nextDouble();

        if (Math.abs(x) >= 1) {
            System.out.println("Ошибка: |x| должно быть меньше 1 (R = 1)");
            return;
        }

        System.out.print("Введите n: ");
        int n = sc.nextInt();

        System.out.print("Введите e: ");
        double e = Math.abs(sc.nextDouble());

        Result r = calculate(x, n, e);

        System.out.println("\nРезультаты:");
        System.out.printf("1) Сумма %d слагаемых: %.10f%n", r.n, r.sn);
        System.out.printf("2) Сумма %d слагаемых |a_k| > e: %.10f%n", r.ne, r.se);
        System.out.printf("3) Сумма %d слагаемых |a_k| > e/10: %.10f%n", r.ne10, r.se10);
        System.out.printf("4) Точное значение функции: %.10f%n", r.f);
        System.out.printf("Погрешность: %.2e%n", Math.abs(r.sn - r.f));
    }
}
