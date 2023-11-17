package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        try {
            // Configuración de Hibernate
            SessionFactory sessionFactory = BaseDeDatos.getSessionFactory();

            while (true) {
                mostrarMenu(sessionFactory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenu(SessionFactory sessionFactory) {
        System.out.println("Menú de Opciones:");
        System.out.println("1. Agregar Usuario");
        System.out.println("2. Autenticar Usuario");
        System.out.println("3. Agregar Perro");
        System.out.println("4. Modificar Perro");
        System.out.println("5. Eliminar Perro");
        System.out.println("6. Mostrar Todos los Perros");
        System.out.println("7. Búsqueda de Perros por Nombre");
        System.out.println("8. Búsqueda con Filtrado (Raza y Edad)");
        System.out.println("9. Recuperar Último Perro Eliminado");
        System.out.println("10. Eliminar Todos los Datos del Programa");
        System.out.println("11. Salir");
        System.out.print("Seleccione una opción: ");

        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        switch (opcion) {
            case 1:
                agregarPerro(sessionFactory);
                break;
            case 2:
                autenticarUsuario(sessionFactory);
                break;
            case 3:
                agregarPerro(sessionFactory);
                break;
            case 4:
                modificarPerro(sessionFactory);
                break;
            case 5:
                eliminarPerro(sessionFactory);
                break;
            case 6:
                mostrarTodosLosPerros(sessionFactory);
                break;
            case 7:
                busquedaDePerros(sessionFactory);
                break;
            case 8:
                busquedaConFiltrado(sessionFactory);
                break;
            case 9:
                recuperarUltimoElementoBorrado(sessionFactory);
                break;
            case 10:
                eliminarTodosLosDatos(sessionFactory);
                break;
            case 11:
                System.out.println("¡Hasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida. Por favor, elija una opción del menú.");
        }
    }

    private static void agregarPerro(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Implementa la lógica para agregar un perro a la base de datos
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nombre del perro: ");
            String nombre = scanner.nextLine();
            System.out.print("Raza: ");
            String raza = scanner.nextLine();
            System.out.print("Edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            System.out.print("Color: ");
            String color = scanner.nextLine();
            System.out.print("Dueño: ");
            String dueño = scanner.nextLine();
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            String fechaNacimiento = scanner.nextLine();

            Perro perro = new Perro(nombre, raza, edad, color, dueño, fechaNacimiento);

            session.save(perro);
            transaction.commit();

            System.out.println("Perro agregado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void modificarPerro(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el ID del perro a modificar: ");
            int perroId = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Verificar si el perro está marcado como eliminado
            Perro perro = session.get(Perro.class, perroId);
            if (perro != null && !perro.isEliminado()) {
                System.out.print("Nuevo nombre del perro: ");
                String nombre = scanner.nextLine();
                System.out.print("Nueva raza: ");
                String raza = scanner.nextLine();
                System.out.print("Nueva edad: ");
                int edad = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                System.out.print("Nuevo color: ");
                String color = scanner.nextLine();
                System.out.print("Nuevo dueño: ");
                String dueño = scanner.nextLine();
                System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
                String fechaNacimiento = scanner.nextLine();

                perro.setNombre(nombre);
                perro.setRaza(raza);
                perro.setEdad(edad);
                perro.setColor(color);
                perro.setDueño(dueño);
                perro.setFechaNacimiento(fechaNacimiento);

                session.update(perro);
                transaction.commit();

                System.out.println("Perro modificado correctamente.");
            } else {
                System.out.println("No se puede modificar un perro marcado como eliminado o el ID no es válido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void eliminarPerro(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el ID del perro a eliminar: ");
            int perroId = scanner.nextInt();

            Perro perro = session.get(Perro.class, perroId);
            if (perro != null && !perro.isEliminado()) {
                perro.setEliminado(true);
                session.update(perro);
                transaction.commit();

                System.out.println("Perro marcado como eliminado correctamente.");
            } else {
                System.out.println("El perro ya ha sido eliminado anteriormente o no se encontró.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarTodosLosPerros(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Query<Perro> query = session.createQuery("FROM Perro WHERE eliminado = false", Perro.class);
            List<Perro> perros = query.list();

            for (Perro perro : perros) {
                System.out.println(perro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void busquedaDePerros(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el nombre del perro a buscar: ");
            String nombre = scanner.nextLine();

            Query<Perro> query = session.createQuery("FROM Perro WHERE nombre LIKE :nombre AND eliminado = false", Perro.class);
            query.setParameter("nombre", "%" + nombre + "%");

            List<Perro> perros = query.list();
            mostrarResultadoBusqueda(perros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void busquedaConFiltrado(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la raza para filtrar (deje en blanco para omitir): ");
            String raza = scanner.nextLine();
            System.out.print("Ingrese la edad para filtrar (deje en blanco para omitir): ");
            String edadInput = scanner.nextLine();

            String hql = "FROM Perro WHERE eliminado = false";
            if (!raza.isEmpty()) {
                hql += " AND raza LIKE :raza";
            }
            if (!edadInput.isEmpty()) {
                hql += " AND edad = :edad";
            }

            Query<Perro> query = session.createQuery(hql, Perro.class);

            if (!raza.isEmpty()) {
                query.setParameter("raza", "%" + raza + "%");
            }
            if (!edadInput.isEmpty()) {
                int edad = Integer.parseInt(edadInput);
                query.setParameter("edad", edad);
            }

            List<Perro> perros = query.list();
            mostrarResultadoBusqueda(perros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Métodos auxiliares

    private static void mostrarResultadoBusqueda(List<Perro> perros) {
        if (!perros.isEmpty()) {
            for (Perro perro : perros) {
                System.out.println(perro);
            }
        } else {
            System.out.println("No se encontraron perros que coincidan con la búsqueda.");
        }
    }
    private static void autenticarUsuario(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese contraseña: ");
            String contrasena = scanner.nextLine();

            Query<Usuario> query = session.createQuery("FROM Usuario WHERE nombre = :nombre AND contrasena = :contrasena", Usuario.class);
            query.setParameter("nombre", nombreUsuario);
            query.setParameter("contrasena", contrasena);

            List<Usuario> usuarios = query.list();
            if (!usuarios.isEmpty()) {
                usuarioActual = usuarios.get(0);
                System.out.println("Autenticación exitosa. ¡Bienvenido, " + usuarioActual.getNombre() + "!");
            } else {
                System.out.println("Autenticación fallida. Verifique el nombre de usuario y la contraseña.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recuperarUltimoElementoBorrado(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Query<Perro> query = session.createQuery("FROM Perro WHERE eliminado = true ORDER BY id DESC", Perro.class);
            query.setMaxResults(1);
            List<Perro> perrosEliminados = query.list();

            if (!perrosEliminados.isEmpty()) {
                Perro ultimoPerroEliminado = perrosEliminados.get(0);

                System.out.println("Último perro eliminado:");
                System.out.println(ultimoPerroEliminado);

                // Ahora, actualizamos el registro marcando eliminado como false
                ultimoPerroEliminado.setEliminado(false);
                session.update(ultimoPerroEliminado);

                System.out.println("El perro ha sido recuperado y marcado como no eliminado.");
            } else {
                System.out.println("No se encontraron registros eliminados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void eliminarTodosLosDatos(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.print("¿Está seguro de que desea eliminar todos los datos del programa? (S/N): ");
            Scanner scanner = new Scanner(System.in);
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) {
                Query<Perro> query = session.createQuery("FROM Perro", Perro.class);
                List<Perro> perros = query.list();

                for (Perro perro : perros) {
                    session.delete(perro);
                }

                transaction.commit();
                System.out.println(perros.size() + " registros eliminados.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}