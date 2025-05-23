using System;
using System.IO;
using UnityEditor;
using UnityEngine;
using System.Collections;
using cn.fv.unity3d.sdkporter;
using System.Runtime.Serialization.Formatters.Binary;

namespace cn.FlyVerifySDK.Unity
{
	#if UNITY_IPHONE
	[CustomEditor(typeof(FlyVerify))]
	[ExecuteInEditMode]
	public class FlyVerifySDKConfigEditor : Editor {
		string appKey = "";
		string appSecret = "";

		void Awake()
		{
			Prepare ();
		}
			
		public override void OnInspectorGUI()
		{
			EditorGUILayout.Space ();
			appKey = EditorGUILayout.TextField ("FlyVerifyKey", appKey);
			appSecret = EditorGUILayout.TextField ("FlyVerifySecret", appSecret);
			Save ();
		}
			
		private void Prepare()
		{
			try
			{
				var files = System.IO.Directory.GetFiles(Application.dataPath , "FlyVerify.keypds", System.IO.SearchOption.AllDirectories);
				string filePath = files [0];
				FileInfo projectFileInfo = new FileInfo( filePath );
				if(projectFileInfo.Exists)
				{
					StreamReader sReader = projectFileInfo.OpenText();
					string contents = sReader.ReadToEnd();
					sReader.Close();
					sReader.Dispose();
					Hashtable datastore = (Hashtable)MiniJSON.jsonDecode( contents );
					appKey = (string)datastore["FlyVerifyKey"];
					appSecret = (string)datastore["FlyVerifySecret"];
				}
				else
				{
					Debug.LogWarning ("Fly.keypds no find");
				}
			}
			catch(Exception e) 
			{
				if(appKey.Length == 0)
				{
					appKey = "3a2c2a977d3d2";
					appSecret = "bda5943cf321055f80f98bcb43491804";
				}
				Debug.LogException (e);
			}
		}
			
		private void Save()
		{
			try
			{
				var files = System.IO.Directory.GetFiles(Application.dataPath , "FlyVerify.keypds", System.IO.SearchOption.AllDirectories);
				string filePath = files [0];
				if(File.Exists(filePath))
				{
					Hashtable datastore = new Hashtable();
					datastore["FlyVerifyKey"] = appKey;
					datastore["FlyVerifySecret"] = appSecret;
					var json = MiniJSON.jsonEncode(datastore);
					StreamWriter sWriter = new StreamWriter(filePath);
					sWriter.WriteLine(json);
					sWriter.Close();
					sWriter.Dispose();
				}
				else
				{
					Debug.LogWarning ("FlyVerify.keypds no find");
				}
			}
			catch(Exception e) 
			{
				Debug.LogWarning ("error");
				Debug.LogException (e);
			}
		}
	}
	#endif
}