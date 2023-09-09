package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {

    private static final String LIBROS_TXT = "C:\\Users\\Usuario\\IdeaProjects\\Pide_tu_libro\\src\\main\\java\\org\\example\\libros.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Carga la lista de libros desde el archivo de texto
        List<String[]> listaLibros = cargarLibrosDesdeTXT(LIBROS_TXT);

        while (true) {
            System.out.println("=== Menú de Biblioteca ===");
            System.out.println("1. Ver libros disponibles");
            System.out.println("2. Reservar un libro");
            System.out.println("3. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarLibrosDisponibles(listaLibros);
                    break;
                case 2:
                    reservarLibro(scanner, listaLibros);
                    break;
                case 3:
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private static List<String[]> cargarLibrosDesdeTXT(String archivo) {
        List<String[]> listaLibros = new ArrayList<>();

        // Verifica si el archivo existe
        File archivoLibros = new File(archivo);

        if (archivoLibros.exists()) {
            // Si el archivo existe, carga los libros desde él
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        listaLibros.add(partes);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo de libros no existe.");
        }

        return listaLibros;
    }

    private static void mostrarLibrosDisponibles(List<String[]> listaLibros) {
        System.out.println("=== Libros Disponibles ===");
        for (int i = 0; i < listaLibros.size(); i++) {
            String[] libro = listaLibros.get(i);
            if (Boolean.parseBoolean(libro[2])) {
                System.out.println((i + 1) + ". " + libro[0] + " - " + libro[1]);
            }
        }
    }

    private static void reservarLibro(Scanner scanner, List<String[]> listaLibros) {
        mostrarLibrosDisponibles(listaLibros);
        System.out.print("Seleccione el número de libro que desea reservar: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion >= 1 && opcion <= listaLibros.size()) {
            String[] libroSeleccionado = listaLibros.get(opcion - 1);
            if (Boolean.parseBoolean(libroSeleccionado[2])) {
                System.out.println("Reservando el libro: " + libroSeleccionado[0]);
                libroSeleccionado[2] = "false"; // Cambiar el estado a no disponible
                guardarLibrosEnTXT(LIBROS_TXT, listaLibros);
            } else {
                System.out.println("El libro seleccionado no está disponible.");
            }
        } else {
            System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    private static void guardarLibrosEnTXT(String archivo, List<String[]> listaLibros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String[] libro : listaLibros) {
                writer.write(String.join(",", libro));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
