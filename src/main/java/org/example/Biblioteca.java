package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {

    private static final String LIBROS_TXT = "C:\\Users\\alexs\\IdeaProjects\\PideTuLibre\\src\\main\\java\\org\\example\\libros.txt";
    private static List<String[]> listaLibros;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        listaLibros = cargarLibrosDesdeTXT(LIBROS_TXT);

        while (true) {
            mostrarMenuBiblioteca();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarMenuLibrosDisponibles(scanner);
                    break;
                case 2:
                    reservarLibro(scanner);
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

    public static void mostrarMenuBiblioteca() {
        System.out.println("|------- Menú de Biblioteca -------|");
        System.out.println("|                                  |");
        System.out.println("| 1) Ver libros disponibles        |");
        System.out.println("| 2) Reservar libro                |");
        System.out.println("| 3) Salir                         |");
        System.out.println("|----------------------------------|");
    }

    private static List<String[]> cargarLibrosDesdeTXT(String archivo) {
        List<String[]> listaLibros = new ArrayList<>();

        File archivoLibros = new File(archivo);

        if (archivoLibros.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 4) {
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

    private static void mostrarMenuLibrosDisponibles(Scanner scanner) {
        while (true) {
            System.out.println("|----- Ver Libros Disponibles -----|");
            System.out.println("|                                  |");
            System.out.println("| 1. Ver libros disponibles        |");
            System.out.println("| 2. Buscar libros por título      |");
            System.out.println("| 3. Volver al menú principal      |");
            System.out.println("|----------------------------------|");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarLibrosDisponibles();
                    break;
                case 2:
                    buscarLibrosPorTitulo(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private static void mostrarLibrosDisponibles() {
        System.out.println("|--- Libros Disponibles ---|");
        validarLibrosDisponibles();
    }

    private static void validarLibrosDisponibles() {
        for (int i = 0; i < listaLibros.size(); i++) {
            String[] libro = listaLibros.get(i);
            String titulo = libro[0];
            String autor = libro[1];
            String isbn = libro[2];
            int copiasDisponibles = Integer.parseInt(libro[3]);

            System.out.println((i + 1) + ". " + titulo + " - " + autor + " (ISBN: " + isbn + ")");

            if (copiasDisponibles == 0) {
                System.out.println("No quedan copias de este libro.");
            } else {
                System.out.println("Copias disponibles: " + copiasDisponibles);
            }
        }
    }

    private static void buscarLibrosPorTitulo(Scanner scanner) {
        System.out.print("Ingrese el título del libro que desea buscar: ");
        String tituloBuscado = scanner.nextLine();
        List<String[]> librosEncontrados = new ArrayList<>();

        for (String[] libro : listaLibros) {
            if (libro[0].equalsIgnoreCase(tituloBuscado) && Integer.parseInt(libro[3]) > 0) {
                librosEncontrados.add(libro);
            }
        }

        if (!librosEncontrados.isEmpty()) {
            System.out.println("|--- Libros Encontrados ---|");
            for (int i = 0; i < librosEncontrados.size(); i++) {
                String[] libro = librosEncontrados.get(i);
                String titulo = libro[0];
                String autor = libro[1];
                String isbn = libro[2];
                int copiasDisponibles = Integer.parseInt(libro[3]);

                System.out.println((i + 1) + ". " + titulo + " - " + autor + " (ISBN: " + isbn + ")");
                System.out.println("Copias disponibles: " + copiasDisponibles);
            }
        } else {
            System.out.println("No se encontró ningún libro disponible con ese título o no quedan copias.");
        }
    }

    private static void reservarLibro(Scanner scanner) {
        System.out.println("|--- Reservar Libro ---|");
        System.out.println("Seleccione cómo desea buscar el libro:");
        System.out.println("1. Buscar por Título");
        System.out.println("2. Buscar por ISBN");
        System.out.println("3. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                buscarLibroPorTituloYReservar(scanner);
                break;
            case 2:
                buscarLibroPorISBNYReservar(scanner);
                break;
            case 3:
                return;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
                break;
        }
    }

    private static void buscarLibroPorTituloYReservar(Scanner scanner) {
        System.out.print("Ingrese el título del libro que desea reservar: ");
        String tituloBuscado = scanner.nextLine();
        List<String[]> librosDisponibles = new ArrayList<>();

        for (String[] libro : listaLibros) {
            if (libro[0].equalsIgnoreCase(tituloBuscado) && Integer.parseInt(libro[3]) > 0) {
                librosDisponibles.add(libro);
            }
        }

        if (!librosDisponibles.isEmpty()) {
            System.out.println("|--- Libros Disponibles ---|");
            for (int i = 0; i < librosDisponibles.size(); i++) {
                String[] libro = librosDisponibles.get(i);
                String titulo = libro[0];
                String autor = libro[1];
                String isbn = libro[2];
                int copiasDisponibles = Integer.parseInt(libro[3]);

                System.out.println((i + 1) + ". " + titulo + " - " + autor + " (ISBN: " + isbn + ")");
                System.out.println("Copias disponibles: " + copiasDisponibles);
            }

            System.out.print("Seleccione el número de libro que desea reservar o ingrese 0 para volver: ");
            int seleccion = scanner.nextInt();
            scanner.nextLine();

            if (seleccion >= 1 && seleccion <= librosDisponibles.size()) {
                String[] libroSeleccionado = librosDisponibles.get(seleccion - 1);
                System.out.println("Reservando el libro: " + libroSeleccionado[0]);
                int copiasDisponibles = Integer.parseInt(libroSeleccionado[3]);
                copiasDisponibles--; // Reducir la cantidad de copias disponibles en 1
                libroSeleccionado[3] = String.valueOf(copiasDisponibles); // Actualizar la cantidad en el arreglo
                if (copiasDisponibles == 0) {
                    System.out.println("No quedan copias de este libro.");
                }
                guardarLibrosEnTXT(LIBROS_TXT, listaLibros);
            } else if (seleccion == 0) {
                return;
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        } else {
            System.out.println("No se encontró ningún libro disponible con ese título o no quedan copias.");
        }
    }

    private static void buscarLibroPorISBNYReservar(Scanner scanner) {
        System.out.print("Ingrese el ISBN del libro que desea reservar: ");
        String isbnBuscado = scanner.nextLine();
        List<String[]> librosDisponibles = new ArrayList<>();

        for (String[] libro : listaLibros) {
            if (libro[2].equalsIgnoreCase(isbnBuscado) && Integer.parseInt(libro[3]) > 0) {
                librosDisponibles.add(libro);
            }
        }

        if (!librosDisponibles.isEmpty()) {
            System.out.println("|--- Libros Disponibles ---|");
            for (int i = 0; i < librosDisponibles.size(); i++) {
                String[] libro = librosDisponibles.get(i);
                String titulo = libro[0];
                String autor = libro[1];
                String isbn = libro[2];
                int copiasDisponibles = Integer.parseInt(libro[3]);

                System.out.println((i + 1) + ". " + titulo + " - " + autor + " (ISBN: " + isbn + ")");
                System.out.println("Copias disponibles: " + copiasDisponibles);
            }

            System.out.print("Seleccione el número de libro que desea reservar o ingrese 0 para volver: ");
            int seleccion = scanner.nextInt();
            scanner.nextLine();

            if (seleccion >= 1 && seleccion <= librosDisponibles.size()) {
                String[] libroSeleccionado = librosDisponibles.get(seleccion - 1);
                System.out.println("Reservando el libro: " + libroSeleccionado[0]);
                int copiasDisponibles = Integer.parseInt(libroSeleccionado[3]);
                copiasDisponibles--; // Reducir la cantidad de copias disponibles en 1
                libroSeleccionado[3] = String.valueOf(copiasDisponibles); // Actualizar la cantidad en el arreglo
                if (copiasDisponibles == 0) {
                    System.out.println("No quedan copias de este libro.");
                }
                guardarLibrosEnTXT(LIBROS_TXT, listaLibros);
            } else if (seleccion == 0) {
                return;
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        } else {
            System.out.println("No se encontró ningún libro disponible con ese ISBN o no quedan copias.");
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
