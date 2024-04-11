import java.util.List;

class Nodo {
    Person persona;
    Nodo anterior;
    Nodo siguiente;

    public Nodo(Person persona) {
        this.persona = persona;
        this.anterior = null;
        this.siguiente = null;
    }
}

class ListaDobleEnlazada {
    Nodo cabeza;
    Nodo cola;



    public ListaDobleEnlazada() {
        this.cabeza = null;
        this.cola = null;
    }

    public void agregarAlFinal(Person persona) {
        Nodo nuevoNodo = new Nodo(persona);
        if (cabeza == null) {
            cabeza = cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
    }

    public void imprimir() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.persona);
            actual = actual.siguiente;
        }
        System.out.println();
    }

    public void crearDesdeLista(List<Person> lista) {
        for (Person persona : lista) {
            agregarAlFinal(persona);
        }
        imprimir();

    }

    public ListaDobleEnlazada[] split() {
        ListaDobleEnlazada l1 = new ListaDobleEnlazada();
        ListaDobleEnlazada l2 = new ListaDobleEnlazada();

        if (cabeza == null) {
            return new ListaDobleEnlazada[] { l1, l2 };
        }

        Nodo lento = cabeza;
        Nodo rapido = cabeza;

        while (rapido != null && rapido.siguiente != null) {
            rapido = rapido.siguiente.siguiente;
            lento = lento.siguiente;
        }

        l1.cabeza = cabeza;
        l1.cola = lento.anterior;

        if (lento != null) {
            l2.cabeza = lento;
            l2.cola = cola;

            // Separar las listas
            if (l1.cola != null) {
                l1.cola.siguiente = null;
            }
            l2.cabeza.anterior = null;
        }


        return new ListaDobleEnlazada[] { l1, l2 };
    }

    public ListaDobleEnlazada merge(ListaDobleEnlazada l1, ListaDobleEnlazada l2) {
        ListaDobleEnlazada resultado = new ListaDobleEnlazada();

        Nodo actual1 = l1.cabeza;
        Nodo actual2 = l2.cabeza;

        while (actual1 != null && actual2 != null) {
            // Comparar los nombres de las personas en actual1 y actual2
            if (actual1.persona.getNombres().compareTo(actual2.persona.getNombres()) <= 0) {
                resultado.agregarAlFinal(actual1.persona);
                actual1 = actual1.siguiente;
            } else {
                resultado.agregarAlFinal(actual2.persona);
                actual2 = actual2.siguiente;
            }
        }

        // Agregar los elementos restantes de l1 o l2
        while (actual1 != null) {
            resultado.agregarAlFinal(actual1.persona);
            actual1 = actual1.siguiente;
        }

        while (actual2 != null) {
            resultado.agregarAlFinal(actual2.persona);
            actual2 = actual2.siguiente;
        }

        return resultado;
    }

    public ListaDobleEnlazada mergesort() {
        // Si la lista está vacía o tiene un solo elemento, está ordenada
        if (cabeza == null || cabeza.siguiente == null) {
            return this;
        }

        // Dividir la lista en dos mitades
        ListaDobleEnlazada[] partes = split();
        ListaDobleEnlazada l1 = partes[0];
        ListaDobleEnlazada l2 = partes[1];

        // Ordenar cada mitad
        ListaDobleEnlazada listaOrdenada1 = l1.mergesort();
        ListaDobleEnlazada listaOrdenada2 = l2.mergesort();

        // Combinar las listas ordenadas
        return merge(listaOrdenada1, listaOrdenada2);

    }
}