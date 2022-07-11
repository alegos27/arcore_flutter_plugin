import 'package:arcore_flutter_plugin/arcore_flutter_plugin.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class CameraPosition extends StatefulWidget {
  @override
  _CameraPositionState createState() => _CameraPositionState();
}

class _CameraPositionState extends State<CameraPosition> {
  ArCoreController? arCoreController;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Camera position'),
        ),
        body: ArCoreView(
          onArCoreViewCreated: _onArCoreViewCreated,
        ),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.camera_alt),
          onPressed: () async {
            try {
              final position = await arCoreController?.cameraPosition();
              print('Camera position: $position');
            } catch (e) {
              print(e);
            }
          },
        ),
      ),
    );
  }

  void _onArCoreViewCreated(ArCoreController controller) {
    arCoreController = controller;
  }

  @override
  void dispose() {
    arCoreController?.dispose();
    super.dispose();
  }
}
