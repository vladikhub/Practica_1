import java.util.InputMismatchException;
import java.util.Scanner;

// Автор работы: Смольков Владислав Андреевич, КИ23-16/1б
// Практическая работа №1, Вариант 5

class Main {
    // Главный метод, где вызывается Меню,
    // и пользователь выбирает пункт.
    public static void main(String[] args) {
        int[][] ar = new int[0][];
        boolean replace = false;
        Scanner sc = new Scanner(System.in);
        // Зацикленный вывод Меню
        while (true){
            System.out.println();
            System.out.println("___МЕНЮ___");
            System.out.println("1. Создать массив вручную");
            System.out.println("2. Создать случайный массив");
            System.out.println("3. Выполнить замену строки и столбца");
            System.out.println("4. Показать результат");
            System.out.println("5. Выход");
            // Считывание действия пльзователя
            int act = sc.nextInt();
            if (act == 5){break;}
            // switch, принимающий ввод, в качестве кейсов выступают значения пунктов из Меню
            switch (act){
                case 1:
                    System.out.println("Введите матрицу: ");
                    // вызов метода для создания введенной пользователем матрицы
                    ar = create_keybord_array();
                    // проверка, создана матрицы успешна или массив пустой
                    if (ar.length == 0){
                        System.out.println("""
                                              Неправильно введен массив.
                                              Введите его без лишних пробелов.
                                              Элементы массивы должны быть числа.
                                              """);
                    }
                    else{
                        System.out.println("Массив создан");
                    }
                    break;
                case 2:
                    // вызов метода для создания матрицы случайным образом
                    ar = create_random_array();
                    System.out.println("Массив создан");
                    for (int[] y: ar){
                        for (int x: y){
                            System.out.print(x + " ");
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    // проверка, был ли создан массив
                    if (ar.length != 0){
                        // вызов метода для замены строки и столбца
                        create_new_array(ar);
                        System.out.println("Замена прошла успешно");
                        replace = true;
                    }
                    else{
                        System.out.println("Сначала нужно создать массив");
                    }
                    break;
                case 4:
                    // проверка, был ли введен исходный массив
                    // и была ли произведена замена
                    if (ar.length != 0 & replace){
                        // метод для вывода матрицы
                        show_result(ar);
                        replace = false;
                        ar = new int[0][];
                    }
                    else{
                        System.out.println("Сначала нужно сделать замену");
                    }
                    break;
                // исход при неверном выборе пункта
                default:
                    System.out.println("Введите значение от 1 до 5");
            }

        }

    }
    // метод для создания матрицы случайным образом
    public static int[][] create_random_array(){
        // выбор случайного размера массива
        int minValueLen = 3;
        int maxValueLen = 6;
        int len = minValueLen + (int) (Math.random() * (maxValueLen - minValueLen + 1));
        int minValue = 1;
        int maxValue = 100;
        // заполнение массива случайными элементами
        int[][] ar = new int[len][len];
        for (int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                ar[i][j] = minValue + (int) (Math.random() * (maxValue - minValue + 1));
            }
        }

        return ar;
    }
    // метод для создания матрицы, введенной пользователем
    public static int[][] create_keybord_array(){
        Scanner sc = new Scanner(System.in);
        String str = "";
        int counter = 0;
        int setup_len = 0;
        boolean correctInput = true;
        // зацикленный ввод строк, состоящих из элементов в одной строке
        // до тех пор, пока не встретится пустая строка
        while(true){
            String current = sc.nextLine();
            counter++;
            if (current.isEmpty()){break;}
            // установка определенной размерности матрицы,
            // исходя из размера первой строки
            if (counter == 1){
                setup_len = current.split(" ").length;
            }
            else{
                // проверка на несоответствие длин строк
                if (current.split(" ").length != setup_len){

                    correctInput = false;
                }
            }
            // добавление всех строк в одну переменную через запятую
            str += current + ',';
        }
        // создание строчного массива из строк, путем разделение строки по запятым
        String[] str_ar = str.split(",");
        int len = str_ar.length;
        // проверка на правильный ввод и размерность матрицы,
        // при неверном исходе возвращается пустой двумерный массив
        if (setup_len != len || !correctInput){
            return new int[0][];
        }
        // создание двумерного массива
        int[][] ar = new int[len][len];
        for (int i = 0; i < len; i++){
            // считывание каждой строки из массива и вычленение оттуда чисел
            Scanner str_to_int = new Scanner(str_ar[i]);
            for (int j = 0; j < len; j++){
                // проверка на неправильное значение,
                // иначе возвращение пустого двумерного массива
                try{
                    int el = str_to_int.nextInt();
                    ar[i][j] = el;
                } catch (InputMismatchException er){
                    return new int[0][];
                }
            }
        }
        return ar;
    }
    // метод, принимающий исходный двумерный массив
    // для замены строки и столбца в этом же массиве
    public static void create_new_array(int[][] ar){

        int len = ar.length;
        int min_str_index = 0;
        int max_col_index = 0;
        int minel = 9999;
        int maxel = -9999;
        // цикл для поиска индекса строки с минимальным элементом
        // и индекса столбца с максимальным элементом
        for (int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                if (ar[i][j] > maxel){
                    maxel = ar[i][j];
                    max_col_index = j;

                }
                if (ar[i][j] < minel){
                    minel = ar[i][j];
                    min_str_index = i;
                }
            }
        }
        // замена элементов строки и столбца
        for (int i = 0; i < len; i++) {
            int el = ar[min_str_index][i];
            ar[min_str_index][i] = ar[i][max_col_index];
            ar[i][max_col_index] = el;
        }
    }
    // метод, принимающий двумерный массив, для вывода результата
    public static void show_result(int[][] ar){
        System.out.println("Результат замены:");
        for (int[] y: ar){
            for (int x: y){
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}