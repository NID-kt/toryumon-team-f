package com.example.runningavater.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.runningavater.ui.theme.SungYellow
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode

@Composable
fun Bear3D(
    assetFileLocation: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SungYellow,
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val cameraNode =
        rememberCameraNode(engine).apply {
            // TODO x, yの値をチェックする
            position = Position(x = +0.000f, y = 0.218f, z = -2.225f)
        }
    val centerNode =
        rememberNode(engine)
            .addChildNode(cameraNode)

    Box {
        Scene(
            modifier =
                modifier.background(
                    color = backgroundColor,
                ),
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
            isOpaque = false,
            onFrame = {
                cameraNode.lookAt(centerNode)
            },
        )
    }
}
