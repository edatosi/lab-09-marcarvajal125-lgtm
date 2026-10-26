
package listas;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 *    Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test). 
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 *    java.util.LinkedList ni ArrayList dentro de esta clase.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        return cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        // Cree un Nodo, enlácelo a la cabeza actual
        // y actualice la cabeza y la cantidad.
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
        // Si la lista está vacía es igual que agregarAlInicio;
        // si no, recorra hasta el último nodo y enlace el nuevo.
        if (cabeza == null) {
            agregarAlInicio(dato);
            return;
        }

        Nodo<T> ultimo = cabeza;

        while (ultimo.getSiguiente() != null) {
            ultimo = ultimo.getSiguiente();
        }

        ultimo.setSiguiente(new Nodo<>(dato));
        cantidad++;
    }

    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista está vacía");
        }

        return cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista está vacía");
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
        // Valide el índice y recorra la lista 'indice' veces.
        validarIndice(indice);
        return nodoEn(indice).getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        // Use indiceDe o recorra la lista.
        // Cuidado: para comparar use Objects.equals(a, b).
        return indiceDe(dato) != -1;
    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        Nodo<T> actual = cabeza;
        int i = 0;

        while (actual != null) {
            if (Objects.equals(actual.getDato(), dato)) {
                return i;
            }

            actual = actual.getSiguiente();
            i++;
        }

        return -1;
    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
        // Casos: indice 0 (inicio), o detenerse en el nodo
        // ANTERIOR a la posición y enlazar allí el nuevo nodo.
        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException(
                    "Índice: " + indice + ", tamaño: " + cantidad);
        }

        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }

        Nodo<T> anterior = nodoEn(indice - 1);
        Nodo<T> nuevo = new Nodo<>(dato);

        nuevo.setSiguiente(anterior.getSiguiente());
        anterior.setSiguiente(nuevo);
        cantidad++;
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        validarIndice(indice);

        Nodo<T> nodo = nodoEn(indice);
        T anterior = nodo.getDato();

        nodo.setDato(dato);

        return anterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        // Mueva la cabeza al siguiente nodo.
        if (cabeza == null) {
            throw new NoSuchElementException("La lista está vacía");
        }

        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        cantidad--;

        return dato;
    }

    /**
     * Elimina y retorna el último elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        // Hay que detenerse en el PENULTIMO nodo.
        // Atención al caso de un solo elemento.
        if (cabeza == null) {
            throw new NoSuchElementException("La lista está vacía");
        }

        if (cabeza.getSiguiente() == null) {
            return eliminarPrimero();
        }

        Nodo<T> penultimo = cabeza;

        while (penultimo.getSiguiente().getSiguiente() != null) {
            penultimo = penultimo.getSiguiente();
        }

        T dato = penultimo.getSiguiente().getDato();
        penultimo.setSiguiente(null);
        cantidad--;

        return dato;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        validarIndice(indice);

        if (indice == 0) {
            return eliminarPrimero();
        }

        Nodo<T> anterior = nodoEn(indice - 1);
        Nodo<T> objetivo = anterior.getSiguiente();

        anterior.setSiguiente(objetivo.getSiguiente());
        cantidad--;

        return objetivo.getDato();
    }

    /**
     * Elimina la primera aparición del dato.
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        // Se apoya en indiceDe y eliminar(int).
        int indice = indiceDe(dato);

        if (indice == -1) {
            return false;
        }

        eliminar(indice);
        return true;
    }

    /** Deja la lista vacía. */
    public void vaciar() {
        cabeza = null;
        cantidad = 0;
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        // Recorra la lista con un StringBuilder.
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;

        while (actual != null) {
            sb.append(actual.getDato());

            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }

            actual = actual.getSiguiente();
        }

        sb.append("]");
        return sb.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        // Use tres referencias: anterior, actual, siguiente.
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;

        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }

        cabeza = anterior;
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista está vacía");
        }

        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;

        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }

        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            Nodo<T> previo = actual;

            while (previo.getSiguiente() != null) {
                if (Objects.equals(
                        previo.getSiguiente().getDato(),
                        actual.getDato())) {

                    previo.setSiguiente(
                            previo.getSiguiente().getSiguiente());

                    cantidad--;
                } else {
                    previo = previo.getSiguiente();
                }
            }

            actual = actual.getSiguiente();
        }
    }

    // =====================================================================
    // Métodos auxiliares privados
    // =====================================================================

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                    "Índice: " + indice + ", tamaño: " + cantidad);
        }
    }

    /** Retorna el nodo en la posición dada (el índice ya debe ser válido). */
    private Nodo<T> nodoEn(int indice) {
        Nodo<T> actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual;
    }
}
