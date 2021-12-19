package edu.yu.introtoalgs;

/** Defines and implements the AnthroChassidus API per the requirements
 * documentation.
 *
 * @author Avraham Leff
 */

public class AnthroChassidus {
  //use union find
  private class CompressedUF{
    private int[] ids;
    private int[] sizes;
    private int groups;
    CompressedUF(int length){//, int[] data
      this.groups = length;//population size
      this.ids = new int[length];
      this.sizes = new int[length];
      for(int id = 0; id < length; id++){
        this.ids[id] = id;
        this.sizes[id] = 1;
      }
    }

    int groups(){
      return this.groups;
    }

    void standardizeSizes(int idA, int idB){//this is nigh O(1) becasue of earlier path compression like union is (a.k.a amortized O(1))
      sizes[idA] = sizes[find(idA)];
      sizes[idB] = sizes[find(idB)];
    }

    int getChassidusGroupSize(int id){// returns the size of the group (a.k.a how many share the same chassidus as "id")
      return sizes[id];
    }

    int find(int p){
      while(p != ids[p]){
        p = ids[p];
      }
      return p;
    }

    int findCompression(int p){
      int x = p;
      while(x != ids[p]){//while x doesnt equal its parent (i.e. x is not root)
        p = ids[p];//set p to its parent
        if( p == ids[p] ){//if p is the root now
          int temp = ids[x];//store p's original parent in a temp
          ids[x] = p;//change x's parent to now be root
          x = ids[temp];//change x variable to now be the value stored at the original parent and continue loop to change all checked parents until x == root
        }
      }
      return p;
    }

    void union(int p, int q){
      int i = findCompression(p);
      int j = findCompression(q);
      if( i == j ) return;

      if(sizes[i] < sizes[j] ){
        ids[i] = ids[j];
        sizes[j] += sizes[i];
      }else{
        ids[j] = ids[i];
        sizes[i] += sizes[j];
      }
      groups--;

    }
  }
  private int lowerBound;
  private CompressedUF compressedUF;

  /** Constructor.  When the constructor completes, ALL necessary processing
   * for subsequent API calls have been made such that any subsequent call will
   * incur an O(1) cost.
   *
   * @param n the size of the underlying population that we're investigating:
   * need not correspond in any way to the number of people actually
   * interviewed (i.e., the number of elements in the "a" and "b" parameters).
   * Must be greater than 2.
   * @param a interviewed people, element value corresponds to a unique "person
   * id" in the range 0..n-1
   * @param b interviewed people, element value corresponds to a unique "person
   * id" in the range 0..n-1.  Pairs of a_i and b_i entries represent the fact
   * that the corresponding people follow the same Chassidus (without
   * specifying what that Chassidus is).
   */
  public AnthroChassidus(final int n, final int[] a, final int[] b) {
    /**
     * 1. I checked the sizes, if they are the same I make a standard length variable 
     * 2. I construct a UF data structure with an array the size of the population an O(n) time move
     * 3. I go through each element index for the arrays and unify them. O(n) explained in 3a
     * 3a. Per Slides and Sedgwick even though this looks like n^2 its actually n here and union is amortized O(1). explained further in write-up
     * 4. We do further path compression and standardize sizes so we can call nShare on any element and get a constant return.  O(n) explained 4a
     * 4a. This is also an O(n) like union, and even more so as we already did path compression once, so this is even faster. 
     * 5. Get lowerBoundOnChassidusTypes by returning compressedUF.groups() which gives the lowerBound as groups starts as population size and gets decreased by 1 per union
     */
    if( a.length != b.length ){
      throw new IllegalArgumentException("Arrays are of different sizes");
    }
    int standardLength = a.length;
    compressedUF = new CompressedUF(n);
    for( int i = 0; i < standardLength; i++ ){
      compressedUF.union(a[i], b[i]);//this is near constant so this loop is O(n)
    }
    //we need to standardize the sizes so that each element has size equal to the root for the nShareMethod
    for( int i = 0; i < standardLength; i++ ){//its amortized O(1) like the union method due to path compression
      compressedUF.standardizeSizes(a[i], b[i]);
    }
    lowerBound = compressedUF.groups();
  }

  /** Return the tightest value less than or equal to "n" specifying how many
   * types of Chassidus exist in the population: this answer is inferred from
   * the interviewers data supplied to the constructor
   *
   * @return tightest possible lower bound on the number of Chassidus in the
   * underlying population.
   */
  public int getLowerBoundOnChassidusTypes() {
    return lowerBound;
  }

  /** Return the number of interviewed people who follow the same Chassidus as
   * this person.
   *
   * @param id uniquely identifies the interviewed person
   * @return the number of interviewed people who follow the same Chassidus as
   * this person.
   */
  public int nShareSameChassidus(final int id) {
    return compressedUF.getChassidusGroupSize(id);
  }

} // class

