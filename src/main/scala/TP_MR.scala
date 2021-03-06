import scala.collection.MapView
import scala.io.BufferedSource

object TP_MR {
  /**
   * Answer question from 1 to 3
   * A) first using : match, case, ::, Nil or foldLeft
   * B) then using method of the List collection
   *
   * To run all the tests after your answer, type the command sbt test in the IntelliJ terminal
   * Example :
   * - type "sbt test" without the " in the terminal
   * - You will see that there is 11 TESTS FAILED
   * - Now write "Hello" instead of the ??? in the giveMeHelloString function
   * - rerun sbt test
   * - You will see  "Tests: succeeded 1, failed 10, canceled 0, ignored 0, pending 0" and if you scroll up you will see
   * - giveMeHelloString function in yellow/green which means that the test passed successfully
   *
   * Do this to check your answer after each exercise
   */

  def giveMeHelloString : String = "Hello"

  /**
   * 1) Find the last elements of a list.
   *
   * Example :
   * scala> last(List(1, 1, 2, 3, 5, 8))
   * res0: Option[Int] = Some(8)
   */

  //TODO define lastA using : match, case, ::, Nil
  def lastA[T](list: List[T]): Option[T] = list match{

    case Nil => None
    case x::Nil => Some(x)
    case x::tail => lastA(tail)

  }

  //TODO define lastB using method of the List collection
  def lastB[T](list: List[T]): Option[T] = list match {
    
    case Nil => None
    case x::tail => Some(list.last)

  }

  /**
   * 2) Find the Kth element of a list:
   * By convention, the first element in the list is element 0.
   *
   * Example:
   * scala> nth(2, List(1, 1, 2, 3, 5, 8))
   * res0: Option[Int] = Some(2)
   */

  //TODO define nthA using : match, case, ::, Nil
  def nthA[T](x: Int, l: List[T]): Option[T] = (x,l) match {
    //Cas d'une liste vide
    case (_, Nil) => None
    //Cas d'une liste vide mais k=0
    case (0,x::_) => Some(x)
    //Cas d'une liste vide mais k#0
    case (x, head::tail) if x>0 => nthA(x-1,tail)
    //Cas de k<0
    case (x, _::tail) if x<0 => None
  }


  //TODO define nthB using method of the List collection
  def nthB[T](x: Int, l: List[T]): Option[T] = {

    if (x > 0 && x < l.length) 
      Some(l(x)) 
    else 
      None

  }

  /**
   * 3) Reverse a list:
   *
   * Example:
   * scala> reverse(List(1, 1, 2, 3, 5, 8))
   * res0: List[Int] = List(8, 5, 3, 2, 1, 1)
   */

  //TODO define reverseA using : foldLeft, ::
  def reverseA[T](list: List[T]): List[T] = list.foldLeft(List.empty[T])( (acc, el) => el:: acc)
 

  //TODO define reverseB using method of the List collection
  def reverseB[T](list: List[T]): List[T] = list.reverse

  /**
   * 4) Sum of wages:
   * With the case class Employee defined below, find the sum of salaries from a list of employees (ideally with map)
   *
   * Example:
   * scala> salarySum(List(Employee("Jon", 2000), Employee("Jane", 3500)))
   * res0: Double = 5500.0
   */

  case class Employee(name: String, salary: Double)

  //TODO define salarySum which is the sum of the salaries from a list of employees
  //Hint it's a map reduce
  def salarySum(employees: List[Employee]): Double = employees.map(_.salary).sum

  def salarySumB(employees: List[Employee]): Double = employees.foldLeft(0.0) { (acc, elem) => acc + elem.salary }

  /**
   * 5) Address list:
   * With the case class User defined below, list the all their addresses
   *
   * Example:
   * scala> addressOf(List(User("Jon", "5 Av. des Champs-??lys??es, Paris"), User("James","17 Boulevard Poissonni??re, Paris")))
   * res0: List[String] = List("5 Av. des Champs-??lys??es, Paris","17 Boulevard Poissonni??re, Paris")
   */

  case class User(name: String, address: String)

  //TODO define addressOf which gives a list of addresses from a list of users
  def addressOf(users: List[User]): List[String] = users.map(_.address)
  //def addressOfB(users:List[User]): List[String] = users.foldRight(List.empty[String])( (acc,elem) => elem.address::acc )

  /**
   * 6) Define the average function (without .toList, or duplicates):
   *
   * Example:
   * scala> average(Iterator(1, 2, 3, 4, 5, 6, 7, 8))
   * res0: Option[Double] = Some(4.5) 
   */

  //TODO define average which takes an Iterator of Double in parameter.
  //you can't use size, length ???
  //You can't use toList, toIterable, to whatever ???
  // values.foldLeft(iterator.empty[Double])( (acc,el) => )
  def average(values: Iterator[Double]): Option[Double] = {
    //Si la liste est vide on retourne rien sinon on calcule
    if ( values.isEmpty){
      None
    }else{
      val (total,count) = values.foldLeft(0.0,0)({ case ((total,count),elem) => (total+elem, count+1) })
      Some(total.toDouble /count)
    }
    
  }
 
  /**
   * 7) Eliminate consecutive duplicates of list elements.
   *If a list contains repeated elements they should be replaced with a single copy of the element. The
   *order of the elements should not be changed.
   *Example:
   *scala> compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
   *res0: List[Symbol] = List('a, 'b, 'c, 'a, 'd, 'e)
   */
  
  //TODO define compress which eliminates consecutive duplicates of list elements
  def compress[T](list: List[T]): List[T] = {
    list match {
      case Nil => Nil
      case x::Nil => List(x)
      case x::y::tail => if (x==y) compress(y::tail) else x::compress(y::tail)
    }
  }
  /**
    * 8) Run-length encoding of a list.
    *Consecutive duplicates of elements are encoded as tuples (N, E) where N is the number of
    *duplicates of the element E.
    *Example:
    *scala> encode(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
    *res0: List[(Int, Symbol)] = List((4,'a), (1,'b), (2,'c), (2,'a), (1,'d), (4,'e))
  */
  //TODO define encode which encodes a list of elements
  def encode[T](list: List[T]): List[(Int,T)] = {
    list match {
      case Nil => Nil
      case x::Nil => List((1,x))
      case x::y::tail => if (x==y) (1+encode(y::tail).head._1,x)::encode(y::tail).tail else (1,x)::encode(y::tail)
    }
  }

  /**
   * 9) Modified run-length encoding.
   * Modify the result of problem P08 in such a way that if an element has no duplicates it is simply copied into the result list.
   * Only elements with duplicates are transferred as (N, E) terms.
   * Example:
    * scala> encodeModified(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
    * res0: List[Any] = List((4,'a), 'b, (2,'c), (2,'a), 'd, (4,'e))
    * Note: the solution should not look at the elements in the list but should create a new list.
    */
  //TODO define encodeModified which encodes a list of elements
  def encodeModified[T](list: List[T]): List[Any] = {
    list match {
      case Nil => Nil
      case x::Nil => List(x)
      case x::y::tail => if (x==y) (1+encodeModified(y::tail).head._1,x)::encodeModified(y::tail).tail else (1,x)::encodeModified(y::tail)
    }
  }

  /**
   * 10) Run-length encoding of a list (direct solution).
   * Implement the so-called run-length encoding data compression method directly.
   * I.e. don't use other methods you've written (like P09's pack) to solve this problem.
   * Example:
   * scala> encodeDirect(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
   * res0: List[(Int, Symbol)] = List((4,'a), (1,'b), (2,'c), (2,'a), (1,'d), (4,'e))
   */
  //TODO define encodeDirect which encodes a list of elements
  def encodeDirect[T](list: List[T]): List[(Int,T)] = {
    list match {
      case Nil => Nil
      case x::Nil => List((1,x))
      case x::y::tail => if (x==y) (1+encodeDirect(y::tail).head._1,x)::encodeDirect(y::tail).tail else (1,x)::encodeDirect(y::tail)
    }
  }

  /**
   * 11) Decode a run-length encoded list.
   * Given a run-length code list generated as specified in problem P09, construct its uncompressed version.
   * Example:
    * scala> decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))
    * res0: List[Symbol] = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
    * Note: the solution should not look at the elements in the list but should create a new list.
  */
  //TODO define decode which decodes a list of elements
  def decode[T](list: List[(Int,T)]): List[T] = {
    list match {
      case Nil => Nil
      case x::Nil => List.fill(x._1)(x._2)
      case x::y::tail => List.fill(x._1)(x._2):::decode(y::tail)
    }
  }



  /**
   * 12) Find the penultimate (last but one) element of a list .
   * Example:
   * scala> penultimate(List(1, 1, 2, 3, 5, 8))
   * res0: Option[Int] = Some(5)
   */
  //TODO define penultimate which returns the penultimate element of a list
  def penultimate[T](list: List[T]): Option[T] = {
    list match {
      case Nil => None
      case x::Nil => None
      case x::y::Nil => Some(x)
      case x::y::tail => penultimate(y::tail)
    }
  }

  /**
   * 2) Find the length/ number of elements of a list.
Example:
scala> length(List(1, 1, 2, 3, 5, 8))
res0: Int = 6
*/
  //TODO define length which returns the length of a list
  def length[T](list: List[T]): Int = {
    list match {
      case Nil => 0
      case x::Nil => 1
      case x::y::tail => 1+length(y::tail)
    }
  }
   

  /**
   * 1) Monoids and almost MapReduce
   *
   * /////////////////////
   * Extract from an article :
   *
   * "un mono??de est une structure alg??brique correspondant ?? un ensemble (les valeurs) avec un ??l??ment neutre (la valeur initiale)
   * et une loi de composition interne associative (l'op??ration binaire).
   * C'est souvent not?? (E, ???, e), avec E l'ensemble de valeurs, ??? la loi de composition interne et e l'??l??ment neutre.
   *
   * Alors, on dit loi de composition, car l'id??e de notre op??ration est de combiner deux ??l??ments de notre ensemble de valeurs
   * (ie. de les composer, d'ailleurs on devrait pouvoir parler d'agr??ger des valeurs).
   * On dit interne car en combinant ces deux valeurs, notre op??ration retourne une nouvelle valeur
   * qui fait partie de notre ensemble initial de valeurs (ie. on ne sort pas de cet ensemble).
   *
   * Pour associative, h?? bien... Si nous utilisons plusieurs fois notre op??ration dans une expression,
   * il est alors possible d'??valuer l'expression quelque soit l'endroit o?? on commence ?? l'??valuer.
   * C'est-??-dire qu'avec l'expression a + b + c, je peux tr??s bien commencer par x = a + b
   * et ensuite faire x + c ou commencer par x = b + c et faire a + x apr??s.
   * Au final, on note ??a (a + b) + c = a + (b + c). L'ordre d'??valuation des sous-expressions n'a pas d'importance pour un mono??de.
   *
   * C'est une propri??t?? int??ressante, car ??a permet de d??couper une expression en sous-expression et d'??valuer ces sous-expressions en parall??le,
   * puis de r??cup??rer et combiner les r??sultats de ces ??valuations pour obtenir le r??sultat final...
   * Et comme ??a, on vient de r??inventer MapReduce !"
   *
   *
   *
   * Voici une liste de stations de ski avec notamment leur localisation et une ??valuation sur 5.
   *
   * Nous avons ci-dessous un extrait d'une liste des stations de ski en France.
   * Bon... Ce n'est pas du big data, hein ! Mais on va faire comme si ????.
   *
   * Cette liste n'est pas compl??te en terme d'information.
   * Malgr?? ??a, nous allons donner la moyenne des ??valuations... tel que le ferait MapReduce ou Spark.
   *
   * Dans MapReduce, il y a une ??tape de pr??paration des donn??es o?? on part d'un fichier pour le convertir en un ensemble cl??/valeur.
   *
   * /////////////////////
   */

  /*TODO
     Now, define getRatingsByDepartement, imagine reading the file "ski_stations_ratings.csv" in the folder Resources and getting an Iterable[String] for each row
     Note that the rating is in position 1 in each line and the department in position 6
   */
  import scala.util.Try

  // we want to get a Map where the key is departement and the value an iterable of all the ratings of that departement
  def getRatingsByDepartement(lines: Iterable[String]): Map[String, Iterable[Double]] = {
    // drop CSV header
    val data: Iterable[String] = lines.drop(1)

    val rows: Iterable[Array[String]] =
      data.map { line =>
        //TODO cleaning line and separate fields by the comma character
        val row: Array[String] = line.trim.split(",")

        // cleansing: if fields are missing, we pad row with empty strings
        row.padTo(7, "")
      }

    // we want an Iterable consisting of the pair Departement and Rating
    val deptRatings: Iterable[(String, Double)] =
      //TODO we remove lines with no departement
      
        //then we map the creation of the tuple, just uncomment
       rows.filter( e=> !e.isEmpty).map(
         fields => (fields(6), Try { fields(1).toDouble }.getOrElse(0.0))
        )

    deptRatings
      .groupBy { case (departement, rating) => departement }
      .view.mapValues(row => row.map { case (departement, rating) => rating }).toMap
  }

  /**
   * /////////////////////
   * From the article:
   *
   * La fonction getRatingsByDepartement ex??cute en fait un shuffle (ie. une redistribution des donn??es)
   * en r??alisant un partitionnement utilisant le d??partement comme cl?? (ce qui n'est pas la meilleure des cl??s,
   * dans la mesure o?? la r??partition des donn??es dans les diff??rentes partitions sera ici d??s??quilibr??e,
   * puisque par exemple dans les Vosges il n'y a pas beaucoup de stations contrairement ?? la Haute-Savoie...
   * Mais, bon. Ce n'est comme si on pouvait faire du big data avec l'??num??ration des stations de ski en France).
   * Ici, ?? chaque cl?? correspond une partition des ??valuations.
   * Dans le cadre de MapReduce, chaque partition serait d??pos??e dans des n??uds diff??rents du cluster.
   *
   * Il va maintenant falloir calculer la moyenne. En supposant, qu'on ait ?? faire ?? une liste immense,
   * il est plus int??ressant de parcourir cette liste en une seule passe qu'en deux.
   * Car pour calculer une moyenne, il faut d'un c??t?? une somme de valeurs et de l'autre leur quantit??, avant de diviser ces deux r??sultats.
   * Ce qui normalement implique deux passes sur notre dataset.
   * Pour le faire en une seule passe, nous allons calculer la somme et la quantit?? en m??me temps,
   * en stockant les r??sultats interm??diaires dans un couple de valeurs (somme, quantit??).
   *
   * Alors, il existe diff??rentes approches pour impl??menter ce calcul de moyenne.
   * Pour l'exercice ici, nous allons ??tudier une solution mettant en avant la notion de mono??de,
   * en se basant sur une typeclasse (un peu ?? la mani??re de la biblioth??que Scala Cats).
   *
   * En Scala, pour d??clarer une typeclasse Monoid, il faut d??clarer un trait g??n??rique,
   * o?? le param??tre A repr??sente le type qui sera qualifi?? de mono??de.
   * Ce trait contient deux m??thodes empty qui retourne l'??l??ment neutre et combine qui permet de combiner deux ??l??ments de A.
   *
   * /////////////////////
   */

  trait Monoid[A] {
    def empty: A
    def combine(a: A, b: A): A
  }

  object Monoid {
    //this will allows us to write Monoid[Int].empty instead of implicitly[Monoid[Int]].empty
    @inline def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
  }

  /**
   * Let's declare a few instances of our typeclass Monoid, that will help us solve our problem
   */

  // TODO Monoid (Int, +, 0)
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = 0 //??? 
    override def combine(a: Int, b: Int): Int = a + b //???
  }

  // TODO Monoid (Double, +, 0.0)
  implicit val doubleMonoid: Monoid[Double] = new Monoid[Double] {
    override def empty: Double = 0.0 // ??? 
    override def combine(a: Double, b: Double): Double = a + b //??
  }

  // TODO turn any tuple (A, B) into Monoid, providing A and B both are Monoid
  implicit def tupleMonoid[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      override def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty) //??? 

      override def combine(left: (A, B), right: (A, B)): (A, B) = (Monoid[A].combine(left._1,right._1),Monoid[B].combine(left._2,right._2)  )
    }

  /**
   * Let's add to some collection the operation combineAll, in which if the values of the collection are from a monoid,
   * combine all the values in order to have only one result
   */

  implicit class iterableWithCombineAll[A: Monoid](l: Iterable[A]) {
    def combineAll: A = l.fold(Monoid[A].empty)(Monoid[A].combine)
  }

  //our MapReduce program/function
  def skiRatingAverage:Double = {
    /**
     * Let's use our function getRatingsByDepartement with a file to obtain a partionning of the data
     */

    import scala.io.Source

    val file: BufferedSource = Source.fromFile("resources/ski_stations_ratings.csv")
    val partitions: Map[String, Iterable[Double]] = getRatingsByDepartement(file.getLines().to(Iterable))

    /**
     * And now let's do our MapReduce
     */

    // TODO phase 1 (Map): get ratings only and associate the value 1 to the rating (create a pair (rating,1))
    val partitionedRatingWithOne: MapView[String, Iterable[(Double, Int)]] = partitions.mapValues(ratings => ratings.map( rating => (rating,1) ) )

    // TODO phase 2 (Combine): locally sum ratings and 1s for each partition
    val partitionedSumRatingsAndCount: MapView[String, (Double, Int)] = partitionedRatingWithOne.mapValues( data => data.combineAll)

    // TODO phase 3 (Reduce): combine for all partitions the sum of ratings and counts
    val (rating, count) : (Double,Int) = partitionedSumRatingsAndCount.values.combineAll

    rating / count
  }

}
