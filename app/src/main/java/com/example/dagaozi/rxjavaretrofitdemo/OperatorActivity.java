package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Base.IBaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Utils.DataTestManager;
import com.example.dagaozi.rxjavaretrofitdemo.model.Course;
import com.example.dagaozi.rxjavaretrofitdemo.model.Person;
import com.example.dagaozi.rxjavaretrofitdemo.model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

public class OperatorActivity extends BaseActivity implements IBaseSubscriber {
   //创建类操作符（delay除外）
    private static final int rang = 1;
    private static final int defer = 2;
    private static final int just = 3;
    private static final int just1 = 4;
    private static final int from = 5;
    private static final int timer = 6;
    private static final int interval = 7;
    private static final int delay = 8;
    private static final int repeat = 9;
    //变换类操作符
    private static final int buffer = 10;
    private static final int window = 11;
    private static final int window1 = 12;
    private static final int map = 13;
    private static final int cast = 14;
    private static final int flatMap = 15;
    private static final int scan = 16;
    private static final int groupBy = 17;
    private static final int groupBy1 = 18;

    //过滤类操作符
    private static final int throttleWithTimeOut= 19;//时间限流操作符
    private static final int debounce= 20;//时间或函数限流操作符
    private static final int distinct= 21;//去重复
    private static final int elementAt= 211;
    private static final int firstAndLast= 221;
    private static final int skipAndTake= 231;
    private static final int sample= 241;
    private static final int throttleFirst= 251;


    private StringBuilder result = new StringBuilder();
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    @Bind(R.id.btnTest)
    Button btnTest;
    @Bind(R.id.tvResult)
    TextView tvResult;
    Observable justObservable;
    Observable deferObservalbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        deferAndJustPrepare();

    }

    private void deferAndJustPrepare() {

        String time = df.format(new Date());
         justObservable = Observable.just(df.format(new Date()));

         deferObservalbe = Observable.defer(new Func0<Observable<String>>() {
             @Override
             public Observable<String> call() {
                 return Observable.just(df.format(new Date()));
             }
         });
    }

    @OnClick(R.id.btnTest)
    public void btnClink(){
       // rangeTest();
       // deferTest();
        //fromTest();
       // timerTest();
       // intervalText();
        //delayTest();
       // RepeatTest();
        /**变换测试*/
        //bufferTest();
       // windowTest();
       // mapTest();
        //castTest();
        //flatMapTest();
        //groupByTest();
       // scanTest();
        /**过滤操作符*/
        //throttleWithTimeOutTest();
        //debounceTest();
        //distinctTest();
        //ElementAtAndFilterTest();
        //firstAndLastTest();
      // skipAndTakeTest();
        sampleAndThrottleFirst();
    }
    /*******************************创建类操作符示例********************************************/
    private void rangeTest() {
        //发射5个不大于等于的数据
        Observable.range(10, 5).subscribe(new BaseSubscriber<Integer>(this, rang));
    }

    //defer是在订阅那一刻才赋值;just在创建Observable的时候就赋值。
    private void deferTest() {
        //创建justObservable时已经赋值
        //justObservable.subscribe(new BaseSubscriber<String>(this, just));
        //这一刻才赋值
        deferObservalbe.subscribe(new BaseSubscriber<Objects>(this, defer));
    }
    private void fromTest()
    {
        String[] words = {"Hello", "Rx", "Java"};
        //将整个数组作为一个对象一次性发送
      //  Observable.just(words).subscribe(new BaseSubscriber<String[]>(this,just1));
        //将数组元素分批次依次发送（调用3次next方法）
       Observable.from(words).subscribe(new BaseSubscriber<String>(this, from));
    }
    private void timerTest(){
        //延时发送操作，五秒后发射，默认运行在computation Scheduler
        Observable.timer(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Long>(this, timer));
    }
    private void intervalText(){
        //Rxjava的轮询器，间隔发送整数序列（并且可以指定延时），比timer多了轮询。默认运行在computation Scheduler，所以订阅要指定Android主线程,它会一直发送直到订阅者调用unsubscribe()取消订阅为止。
        //下面的列子是三秒后发射Obserble,且每隔五秒发送一次
        Observable.interval(3, 5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Long>(this, interval));
    }
    private void delayTest(){
        //延迟一段指定的时间再发送来自Observable的发送结果。delay级别比timer,interval，just低，跟map一个等级，是用于事件流中的操作,可以延迟发送事件流中的某一次发送。
        Observable.just("延时五秒钟发送我").delay(3,TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<String>(this, delay));
    }
    private void RepeatTest(){
        //默认运行在新线程
        Observable.just("重复发送我5次").delay(3, TimeUnit.SECONDS).repeat(5).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<String>(this, repeat));
    }
    /*************************************变换类操作符示例*********************************************/
    private void  bufferTest(){
      //缓存操作，按数量缓存，收集到两个数据后，把两个数据同时发射出去。还可以加第二个参数skip,buffer(2，3）表示每三个数据发送两个数据（[1,2],[4,5],[7,8]）
     // Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).buffer(2,3).subscribe(new BaseSubscriber<List<Integer>>(this, buffer));
      //缓存操作，按时间缓存，本来一秒钟发射依次，加上buffer(3,.TimeUnit.SECONDS)后，变成缓存3秒后发送
      Observable.interval(1, TimeUnit.SECONDS).buffer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<List<Long>>(this, buffer));
    }
    private void windowTest(){
       // Window操作符类似于buffer，不同之处在于window发射的是一些小的Observable对象，由这些小的Observable对象来发射内部包含的数据。
       // 同buffer一样，window不仅可以通过数目来分组还可以通过时间等规则来分组
        //同buffer操作类似，buffer发送的是数据(Interger)，window发送的是数据流（Observable<Interger>)
      //  Observable.just(1,2,3,4,5,6,7,8,9).window(2).subscribe(new BaseSubscriber<Observable<Integer>>(this,window));
        //按时间
        Observable.interval(1, TimeUnit.SECONDS).window(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Observable<Long>>(this,window1));
    }
   /* 1、map和flatMap都是接受一个函数作为参数(Func1)
    2、map函数只有一个参数，参数一般是Func1，Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据
    3、flatMap函数也只有一个参数，也是Func1,Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据，不过这里O为Observable类型
    flatMap解决1对多的变换*/
   private void mapTest()
   {
       //map是对事件流中的数据做直接变换，事件流还是那个事件流
       Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).map(new Func1<Integer, String>() {
           @Override
           public String call(Integer i) {
               return "整数变字符串:" + i;
           }
       }).subscribe(new BaseSubscriber<String>(this, map));
   }
   // cast(),它是 map() 操作符的特殊版本。它将源Observable中的每一项数据都转换为新的类型，把它变成了不同的 Class 。
    private void castTest(){
      Observable.just(new Person("dagaozi")).cast(Student.class).subscribe(new Action1<Student>() {
          @Override
          public void call(Student student) {
              Log.d("cast", student.getName());
          }
      });
    }
    private  void flatMapTest(){
        //将学生流变换成学生的课程流  flatMap() 不能够保证在最终生成的Observable中源Observables确切的发射顺序。
        // 即不能保证打印的成绩顺序和发送学生的顺序一致。解决方式ConcatMap用法与flatMap一致
        // switchMap() 和 flatMap() 很像，除了一点：每当源Observable发射一个新的数据项（Observable）时，
        // 它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
        Observable.from(DataTestManager.getStudents()).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.getCourseList());
            }
        }).subscribe(new BaseSubscriber<Course>(this, flatMap));
    }
    private void groupByTest(){  //????????
       // GroupBy操作符将原始Observable发射的数据按照key来拆分成一些小的Observable，然后这些小的Observable分别发射其所包含的的数据，类似于sql里面的groupBy。
        //在使用中，我们需要提供一个生成key的规则，所有key相同的数据会包含在同一个小的Observable中。另外我们还可以提供一个函数来对这些数据进行转化，有点类似于集成了flatMap。

        //groupBy操作符是对源Observable产生的结果进行分组，形成一个类型为GroupedObservable的结果集，GroupedObservable中存在一个方法为getKey()，
        // 可以通过该方法获取结果集的Key值（类似于HashMap的key)。值得注意的是，由于结果集中的GroupedObservable是把分组结果缓存起来，
        // 如果对每一个GroupedObservable不进行处理（既不订阅执行也不对其进行别的操作符运算），就有可能出现内存泄露。因此，
        // 如果你对某个GroupedObservable不进行处理，最好是对其使用操作符take(0)处理。
        Observable.from(DataTestManager.getStudents()).groupBy(new Func1<Student, Integer>() {
            @Override
            public Integer call(Student student) {
                //根据key的奇偶进行分组
                return student.getGender();
            }
        }).subscribe(new BaseSubscriber<GroupedObservable<Integer, Student>>(this,groupBy));
    }
    private void scanTest(){
       // Scan操作符对一个序列的数据应用一个函数，并将这个函数的结果发射出去作为下个数据应用这个函数时候的第一个参数使用，有点类似于递归操作

        Observable.just(1, 2, 3, 4).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer sum, Integer i2) {
                //参数sum就是上一次的计算结果
                return sum + i2;
            }
        }).subscribe(new BaseSubscriber<Integer>(this,scan));
    }
   /*******************************************************过滤操作符**********************************************/
   private void throttleWithTimeOutTest(){
       //throttleWithTimeOutTest() 函数过滤掉由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
       //每隔100毫秒发射一个数据，当要发射的数据是3的倍数的时候，下一个数据就延迟到300毫秒再发射。过滤时间为200毫秒，发送速率小于200毫秒的过滤掉
       Observable.create(new Observable.OnSubscribe<Integer>() {
           @Override
           public void call(Subscriber<? super Integer> subscriber) {
               for (int i = 0; i < 10; i++) {
                   if (!subscriber.isUnsubscribed()) {
                       subscriber.onNext(i);
                   }
                   int sleep = 100;
                   if (i % 3 == 0) {
                       sleep = 300;
                   }
                   try {
                       Thread.sleep(sleep);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
               subscriber.onCompleted();
           }
       }).subscribeOn(Schedulers.newThread()).throttleWithTimeout(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Integer>(this,throttleWithTimeOut));
   }
   private void debounceTest(){
       //debounce操作符也可以使用时间来进行过滤，这时它跟throttleWithTimeOut使用起来是一样，但是deounce操作符还可以根据一个函数来进行限流。
       // 这个函数的返回值是一个临时Observable，如果源Observable在发射一个新的数据的时候，上一个数据根据函数所生成的临时Observable还没有结束，那么上一个数据就会被过滤掉。
       //生成一个Observable并使用debounce对其进行过滤，只有发射来的数据为偶数的时候才会调用onCompleted方法来表示这个临时的Observable已经终止。
       Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).debounce(new Func1<Integer, Observable<Integer>>() {
           @Override
           public Observable<Integer> call(final Integer i) {
               return Observable.create(new Observable.OnSubscribe<Integer>() {
                   @Override
                   public void call(Subscriber<? super Integer> subscriber) {
                       if (i % 2 == 0 && !subscriber.isUnsubscribed()) {
                           Log.d("debounce", "临时complete完成:" + i);
                           subscriber.onNext(i);
                           subscriber.onCompleted();
                       }
                   }
               });
           }
       }).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Integer>(this, debounce));
   }
    private void distinctTest(){
        //去除重复
        //Observable.just(1,2,3,4,5,4,3,2,7).distinct().subscribe(new BaseSubscriber<Integer>(this,distinct));
        //去除连续重复
        Observable.just(1, 2, 3, 3, 4, 5, 4, 3).distinctUntilChanged().subscribe(new BaseSubscriber<Integer>(this,distinct));

    }
    public void ElementAtAndFilterTest(){
        //取出第几个数从零开始
     /*   Observable.just(1,3,5,7,9).elementAt(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });*/
        //按条件过滤
        Observable.just(1,2,3,4,6).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });
    }
    private void firstAndLastTest(){
        Observable.just(1,2,3,4,5).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 4;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });
    }
    private void skipAndTakeTest(){
        //跳过前三个
        Observable.just(1,2,3,4,5).skip(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });
        //取前三个
       /* Observable.just(1,2,3,4,5).take(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });*/
    }
    private void sampleAndThrottleFirst(){
        //Sample操作符会定时地发射源Observable最近发射的数据，其他的都会被过滤掉，等效于ThrottleLast操作符，
        // 而ThrottleFirst操作符则会定期发射这个时间段里源Observable发射的第一个数据
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).sample(1000, TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("elementAt", integer + "");
            }
        });//.throttleFirst(1000, TimeUnit.MILLISECONDS);
    }
    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case rang:
                result.append((Integer) o + " ");
                tvResult.setText(result.toString());
                Log.d("Rang", ((Integer) o) + "");
                break;
            case just:
                tvResult.setText((String) o);
            case defer:
                tvResult.setText((String)o);
                break;
            case just1:
                tvResult.setText(((String[]) o).length + "");
                break;
            case from:
                Log.d("from", (String) o);
                break;
            case timer:
                Log.d("timer",df.format(new Date()) );
                break;
            case interval:
                Log.d("interval", df.format(new Date()));//妈蛋，没有subscriber停不下来啦!
                break;
            case delay:
                tvResult.setText((String) o);
                break;
            case repeat:
                tvResult.setText(((String) o + df.format(new Date()).toString()));
                break;
            case buffer:
                Log.d("buffer", ((ArrayList<Integer>) o).toString());
                break;
            case window:
                Log.d("window", ((Observable<Integer>) o).toString());
                ((Observable<Integer>) o).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("window", integer + "");
                    }
                });
                break;
            case window1:
                Log.d("window", ((Observable<Long>) o).toString());
                ((Observable<Long>) o).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long num) {
                        Log.d("window",num+"");
                    }
                });
                break;
            case map:
                Log.d("map", (String) o);
                break;
            case flatMap:
                Log.d("flatMap", ((Course) o).getScore() + "");//打印出所有学生的所选的所有课程的成绩
                break;
            case scan:
                Log.d("flatMap", ((Integer) o) + "");
                break;
            case groupBy:
                Log.d("flatMap", "------>group:"+ ((GroupedObservable<Integer, Student>)o).getKey());
                ((GroupedObservable<Integer, Student>) o).subscribe(new BaseSubscriber<Student>(this, groupBy1));
                break;
            case groupBy1:
                Log.d("groupby", "id=" + ((Student) o).getId() + "   name=" + ((Student) o).getName() + "gender=" + ((Student) o).getGender());
                break;
            /********************过滤操作符******************************/
            case throttleWithTimeOut:
                Log.d("throttleWithTimeOut", ((Integer) o) + "");
                break;
            case debounce:
                Log.d("debounce", ((Integer) o) + "");
                break;
            case distinct:
                Log.d("distinct", ((Integer) o) + "");
                break;
            default:
                Log.d("default", "no fand flag");
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        tvResult.setText(e.getMessage());
    }
}
