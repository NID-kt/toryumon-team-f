package org.nidkt.tekuteku.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.nidkt.tekuteku.R
import kotlin.random.Random

fun notifyMorning(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("今日も一緒に！")
            .setContentText("今日も一日頑張りましょう！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notifyNight(
    context: Context,
    bearname: String,
) {
    // 　ランダムなメッセージのリスト
    val messages =
        listOf(
            "今日、${bearname}はスクワットをしたよ！足がプルプルだけどがんばったよ！",
            "${bearname}は今日ジョギングをしたよ！風が気持ちよくて走りやすかったよ！",
            "${bearname}は今日は腕立て伏せをしたよ！最後の方は腕がプルプルやったけどやり切ったよ！",
            "今日はね${bearname}はストレッチをしたよ！体が伸びてスッキリした気分だよ！",
            "$bearname、今日は腹筋をしたよ！お腹にすごく効いて起き上がれなくなっちゃった....",
            "そういえばね、${bearname}は今日ラジオ体操をしたよ！久しぶりだったけど楽しかったよ！",
            "${bearname}縄跳びをしてみたよ！最初はうまく跳べなかったけど、だんだんリズムに乗れるようになって楽しかったよ",
            "${bearname}今日はヨガをしたよ！深呼吸しながらするとすっごく落ち着いたよ！",
            "${bearname}スクワットジャンプをがんばったよ！普通のスクワットよりきつかったけど、とっても達成感があったよ！",
            "そうそう、${bearname}ダンスもしてみたよ！好きな音楽に合わせて体を動かすのは楽しいね！",
            "今日、${bearname}は階段ダッシュをしたよ！息が切れたけど、運動になったよ！",
        )

    // ランダムにメッセージを選ぶ
    val randomMessage = messages.random()

    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("一日を振り返ろう！")
            .setContentText(randomMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify20Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("２０％達成！")
            .setContentText("まだまだ始まったばっかり！一歩一歩頑張ろう！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify40Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("４０％達成！")
            .setContentText("もうちょっとで折り返し！ファイト！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify50Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("５０％達成！")
            .setContentText("目標まであと半分！折り返しだよ！がんばれ～！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify60Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("６０％達成！")
            .setContentText("ここまでお疲れ！もう４０％がんばれ！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify80Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("８０％達成！")
            .setContentText("すごい！目標達成まであと少しだよ！がんばれ～！！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notify100Goal(context: Context) {
    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("目標達成！")
            .setContentText("目標達成おめでとう！ここまでお疲れ様です！！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}

fun notifyRandom(context: Context) {
    // 　ランダムなメッセージのリスト
    val messages =
        listOf(
            "今日も頑張ってるね！その調子で続けよう！",
            "ちょっと疲れても大丈夫、休んでからまたやればいいよ！",
            "一歩ずつ進んでることが大事だから、自分を誇りに思ってね！",
            "今できることを全力でやってる、それが素晴らしい！",
            "途中で止まっても大丈夫、少しずつでも前に進んでるんだから！",
            "今日の努力が未来の自分に繋がってるから、無理せず頑張ろう！",
            "少しの進歩も大きな一歩！頑張ってる自分を褒めてあげよう！",
            "常に行動することも大事だけど休憩も大事！やりたいときに始めよう！",
            "頑張っている君にしかできないことがあるから、そのまま進んで！",
            "どんな小さな成果も大切！少しずつでも進んでるから、自信を持って！",
            "君がやってること、無駄じゃないよ！続ければ必ず成果が出る！",
            "たとえ今が辛くても、絶対に終わりが見えてくるから信じて！",
            "諦めずに頑張ってる姿、ほんとに素敵だよ！",
            "大丈夫、進んでいる限りは後戻りなんてないから！",
            "自分のペースで進めばいいんだから、焦らずにやろう！",
            "やってること、きっと無駄にはならないよ！一歩一歩進んで！",
            "君の努力は必ず成果に繋がるから、信じて続けよう！",
            "今日は少しだけでも進んだ自分を、しっかり認めてあげて！",
            "挑戦してる君がすでに素晴らしい！途中での疲れも乗り越えられるよ！",
            "君のペースでいいから、焦らず無理せず続けていこうね！",
        )

    // ランダムにメッセージを選ぶ
    val randomMessage = messages.random()

    var builder =
        NotificationCompat.Builder(context, "regularly_notification")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("くまからのお手紙")
            .setContentText(randomMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(Random.nextInt(), builder.build())
    }
}
