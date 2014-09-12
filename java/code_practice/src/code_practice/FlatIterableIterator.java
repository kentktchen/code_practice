package code_practice;

import java.util.Iterator;

import com.google.common.collect.Lists;

@SuppressWarnings({"rawtypes","unchecked"})
class FlatIterableIterator {
    public static void main(String[] args) {
        Iterable iterable = Lists.newArrayList(
                1,
                2,
                Lists.newArrayList(
                        "2.a",
                        Lists.newArrayList(
                                "2.a.ii"),
                        "2.b"),
                        3,
                        4);

        /* expected output:
          Result:
           1
           2
           2.a
           2.a.ii
           2.b
           3
           4
         */

        Iterator iter = createFlattenedIterator(iterable);
        System.out.println("Result:");
        while (iter.hasNext()) {
            System.out.println(" " + iter.next());
        }
        System.out.println();
    }
    public static Iterator createFlattenedIterator(Iterable iterableOfIterables) {
        // implement me
        return new FlatItr(iterableOfIterables);
    }

}

class FlatItr<E> implements Iterator<E> {

    Iterator<E> iterator;
    Iterator<E> temp; // temp iterator


    FlatItr(Iterable itr) {
        this.iterator = itr.iterator();
    }

    public E next() {
        while(temp != null && temp.hasNext()) {
            return temp.next();
        }

        while(iterator.hasNext()) {
            E e = iterator.next();
            if(e instanceof Iterable) {
                temp = new FlatItr((Iterable)e);//((Iterable)e).iterator();
                if (temp.hasNext()) {
                    return temp.next();
                } else {
                    temp = null;
                }
            } else {
                return e;
            } 
        }

        return null;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}

// Standard interfaces for reference below:

// interface Iterator<E> {
//   /**
//    * @return the next element in the collection
//    * @throws NoSuchElementException if there are no more elements
//    */
//   E next();

//   /**
//    * @return true if there is at least one more element in the collection
//    */
//   boolean hasNext();

//   /**
//    * @throws IllegalStateException if next has not yet been called or remove
//    *         has already been called since the last call to next.
//    * @throws UnsupportedOperationException if this Iterator does not support
//    *         the remove operation.
//    */
//   void remove();
// }

// interface Iterable<E> {
//   Iterator<E> iterator();
// }
