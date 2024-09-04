package com.example.runningavater

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

// ProtoDatastoreクラスがプロトコルバッファから生成されたクラスであると仮定します。
class ProtoDatastore private constructor(
    val exampleCounter: Int, // プロトコルバッファからのプロパティをここに追加
) {
    companion object {
        fun getDefaultInstance(): ProtoDatastore {
            // デフォルトインスタンスを返すロジック
            return ProtoDatastore(0)
        }
    }

    fun toBuilder(): Builder {
        return Builder(this)
    }

    class Builder(private val protoDatastore: ProtoDatastore) {
        private var counter: Int = protoDatastore.exampleCounter

        fun setExampleCounter(value: Int): Builder {
            this.counter = value
            return this
        }

        fun build(): ProtoDatastore {
            return ProtoDatastore(counter)
        }
    }
}

object ProtoSettingsSerializer : Serializer<ProtoDatastore> {
    override val defaultValue: ProtoDatastore = ProtoDatastore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoDatastore {
        try {
            // ここでプロトコルバッファのパーサを使う必要があります。
            return ProtoDatastore.getDefaultInstance() // ここで適切なパーサを使ってください。
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: ProtoDatastore,
        output: OutputStream,
    ) {
        // ここでプロトコルバッファのシリアライザを使う必要があります。
    }
}

val Context.settingsDataStore: DataStore<ProtoDatastore> by dataStore(
    fileName = "ProtoSettings.pb",
    serializer = ProtoSettingsSerializer,
)

// Read
fun getExampleCounterFlow(context: Context): Flow<Int> =
    context.settingsDataStore.data
        .map { settings ->
            settings.exampleCounter
        }

// Create
suspend fun incrementCounter(context: Context) {
    context.settingsDataStore.updateData { currentSettings ->
        currentSettings.toBuilder()
            .setExampleCounter(currentSettings.exampleCounter + 1)
            .build()
    }
}
