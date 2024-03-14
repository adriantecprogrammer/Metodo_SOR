package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        double[][] sistema;
        double[] solucion={};
        // Definir el sistema de ecuaciones
        double[][] coeficientes = {
                {9, 2, 3, 16},
                {4, 10, 8, 142},
                {2, 6, 7, 13}
        };
        // Definir las aproximaciones iniciales
        double[] aproximacionesIniciales = {0, 0, 0};

        // Definir el número máximo de iteraciones
        int iteracionesMaximas = 10000;

        // Definir el criterio de convergencia (puedes ajustar este valor según sea necesario)
        double tolerancia = 0.0001;

        // Definir el factor de relajación (puedes ajustar este valor según sea necesario)
        double factorRelajacion = 0.8;

        // Llamar al método para resolver el sistema con relajación


        System.out.println("---------Opcion 1 Resolver sin relajacion  Opcion 2 Resolver con relajacion-----");
        int opcion = leer.nextInt();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Ingresa el sistema de ecuaciones, comenzaras ingresando los coeficientes" +
                "\n de la primer ecuacion y asi sucesivamente" +
                "\nde ser posible ingresa el sistema con su diagonal dominante");
        System.out.println("---------------------------------------------------------------------------\n");

        try{
            if (opcion == 1) {
                sistema = mostrarMatriz();
                System.out.printf("\nENCONTRANDO SOLUCION este proceso puede demorar algunos segundos...\n\n");
                solucion = gaussSeidel(sistema, aproximacionesIniciales, iteracionesMaximas, tolerancia);

            } else if (opcion == 2) {
                System.out.print("Ingresa la relajacion deseada: ");
                double relajacion = leer.nextDouble();
                sistema = mostrarMatriz();
                System.out.printf("\nENCONTRANDO SOLUCION este proceso puede demorar algunos segundos...\n\n");
                solucion = gaussSeidelRelajacion(sistema, aproximacionesIniciales, iteracionesMaximas, tolerancia, factorRelajacion);
            }
        }catch (Exception e ){
            System.out.println("UPSSS ocurrio un error");
        }

        // Imprimir la solución
        System.out.println("Solución:");
        for (int i = 0; i < solucion.length; i++) {
            System.out.println("x" + (i + 1) + " = " + solucion[i]);
        }
    }

    public static double[] gaussSeidelRelajacion(double[][] coeficientes, double[] aproximacionesIniciales, int iteracionesMaximas, double tolerancia, double factorRelajacion) {
        int n = coeficientes.length;
        double[] solucion = new double[n];
        double[] solucionAnterior = new double[n];

        for (int iteracion = 0; iteracion < iteracionesMaximas; iteracion++) {
            for (int i = 0; i < n; i++) {
                solucionAnterior[i] = solucion[i];

                double suma = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        suma += coeficientes[i][j] * solucion[j];
                    }
                }

                // Aqui aplicamos el factor de relajacion
                solucion[i] = (1 - factorRelajacion) * solucion[i] + (factorRelajacion / coeficientes[i][i]) * (coeficientes[i][n] - suma);
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }

            // Verificar la convergencia
            if (converge(solucion, solucionAnterior, tolerancia)) {
                System.out.println("Convergencia alcanzada en la iteración " + (iteracion + 1));
                break;
            }
        }

        return solucion;
    }

    public static double[] gaussSeidel(double[][] coeficientes, double[] aproximacionesIniciales, int iteracionesMaximas, double tolerancia) {
        int n = coeficientes.length;
        double[] solucion = new double[n];
        double[] solucionAnterior = new double[n];

        for (int iteracion = 0; iteracion < iteracionesMaximas; iteracion++) {
            for (int i = 0; i < n; i++) {
                solucionAnterior[i] = solucion[i];

                double suma = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        suma += coeficientes[i][j] * solucion[j];
                    }
                }

                //System.out.println((coeficientes[i][n] - suma) / coeficientes[i][i]);
                solucion[i] = (coeficientes[i][n] - suma) / coeficientes[i][i];
                // System.out.println(solucion[i]);

            }


            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }


            // Verificar la convergencia
            if (converge(solucion, solucionAnterior, tolerancia)) {
                System.out.println("Resultado  alcanzado en la iteración " + (iteracion + 1));
                break;
            }
        }

        return solucion;
    }

    public static double[][] ingresarSistema() {

        String size = "";
        int esize = 0;
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese tamaño del sistema de ecuaciones (solo ingresa el numero, ej: 3 si es de 3 ecuaciones): ");
        size = leer.nextLine();
        esize = Integer.parseInt(String.valueOf(size.charAt(0)));

        double[][] ecuacion = new double[esize][esize + 1];
        for (int i = 0; i < ecuacion.length; i++) {
            for (int j = 0; j < ecuacion[0].length; j++) {
                if (j == ecuacion[0].length - 1) {
                    System.out.println("Ingrese r: ");
                } else {
                    System.out.println("Ingrese el coeficiente x" + (j + 1) + ": ");
                }

                ecuacion[i][j] = leer.nextDouble();
            }

        }
        return ecuacion;
    }
    public static double[][] mostrarMatriz() {

        double[][] matriz = ingresarSistema();

        System.out.print("--------------------------------------\n");
        System.out.print("ESTE ES TU SISTEMA DE ECUACIONES\n");
        System.out.println("-------------------------------------");

        for (int i = 0; i < matriz.length; i++) {
            System.out.print("{");
            for (int j = 0; j < matriz[0].length; j++) {

                System.out.print(matriz[i][j] + ", ");
            }
            System.out.print("}\n");
        }

        return matriz;
    }
    private static boolean converge(double[] solucion, double[] solucionAnterior, double tolerancia) {
        for (int i = 0; i < solucion.length; i++) {
            if (Math.abs(solucion[i] - solucionAnterior[i]) > tolerancia) {
                return false;
            }
        }
        return true;
    }
}
