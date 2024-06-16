package com.example.runningavater

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.ViewNode2
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode

@Composable
fun Bear3D(
    assetFileLocation: String,
    modifier: Modifier = Modifier,
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)
    val cameraNode = rememberCameraNode(engine).apply {
        position = Position(x=0f,y=+1.5f,z = -3f)
    }
    val centerNode = rememberNode(engine)
        .addChildNode(cameraNode)

    Scene(
        modifier = modifier.height(400.dp).heightIn(max=400.dp),
        engine = engine,
        modelLoader = modelLoader,
        cameraNode = cameraNode,
        childNodes = listOf(
            centerNode,
            /*
            Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 1.8. Please specify proper '-jvm-target' option
            というエラーが表示されるかもしれないが、IDEのバグ？っぽく ビルドは通るので無視して良さそう。
            */
            rememberNode {
                ModelNode(
                    modelInstance = modelLoader.createModelInstance(
                        assetFileLocation = assetFileLocation,
                    ),
                    scaleToUnits = 1.0f
                )
            },
        ),


        isOpaque = true,
        //viewNodeWindowManager = ViewNode2.WindowManager(),
        onFrame = {
            cameraNode.lookAt(centerNode)
        },
        environment = environmentLoader.createHDREnvironment(
            assetFileLocation = "snowy_forest_4k.hdr"
        )!!,
    )
}