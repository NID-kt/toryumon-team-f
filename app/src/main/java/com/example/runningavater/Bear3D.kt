package com.example.runningavater

import kotlin.FloatArray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import androidx.compose.ui.graphics.Color

@Composable
fun Bear3D(
    assetFileLocation: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFFEDB81),
) {

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)
    val cameraNode =
        rememberCameraNode(engine).apply {
            //engine.setClearColor(0.99f, 0.86f, 0.51f, 1.0f) // RGBA値 (背景色を指定)

            position = Position(x = 0f, y = +0.9f, z = -2.5f)
        }
    val centerNode =
        rememberNode(engine)
            .addChildNode(cameraNode)

    Scene(
        modifier = modifier
            .fillMaxSize(),
        engine = engine,
        modelLoader = modelLoader,
        cameraNode = cameraNode,
        childNodes =
            listOf(
                centerNode,
            /*
            Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 1.8. Please specify proper '-jvm-target' option
            というエラーが表示されるかもしれないが、IDEのバグ？っぽく ビルドは通るので無視して良さそう。
             */
                rememberNode {
                    ModelNode(
                        modelInstance =
                            modelLoader.createModelInstance(
                                assetFileLocation = assetFileLocation,
                            ),
                        scaleToUnits = 1.0f,
                    )
                },
            ),
        isOpaque = true,
        // viewNodeWindowManager = ViewNode2.WindowManager(),
        onFrame = {
            cameraNode.lookAt(centerNode)
        }
        //背景の森を非表示
//        environment =
//            environmentLoader.createHDREnvironment(
//                assetFileLocation = "snowy_forest_4k.hdr",
//            )!!,
    )
}
