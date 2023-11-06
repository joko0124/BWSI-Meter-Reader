B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.3
@EndOfDesignText@
	#Region  Service Attributes 
		#StartAtBoot: False
		#ExcludeFromLibrary: True
	#End Region

Sub Process_Globals
	'Database
	Public DBCon As SQL
	Public strCriteria As String
	Public DBPath As String
	Public DBName = "MasterDB.db" As String
	Public rp As RuntimePermissions
End Sub

Sub Service_Create
	'	rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL_STORAGE)
	'	rp.CheckAndRequest(rp.PERMISSION_READ_EXTERNAL_STORAGE)
	Dim jo As JavaObject
	jo.InitializeStatic("java.util.Locale").RunMethod("setDefault", Array(jo.GetField("US")))
	
	Log(rp.GetSafeDirDefaultExternal(""))
	DBPath = DBUtils.CopyDBFromAssets(DBName)
	DBCon.Initialize(DBPath, DBName,False)
End Sub
	'SetManifestAttribute(android:installLocation, "auto")
Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

	'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub
