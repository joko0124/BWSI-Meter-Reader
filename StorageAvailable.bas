B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=10
@EndOfDesignText@
#Event: StorageAvailable
Sub Class_Globals
	Private ion As Object
	Private mCallback As Object
	Private mEventName As String

End Sub

Public Sub Initialize (Callback As Object, EventName As String)
	mCallback = Callback
	mEventName = EventName
End Sub

Public Sub HasPermission As Boolean
	Dim has As Boolean
	Dim jo As JavaObject
	jo.InitializeStatic("android.os.Environment")
	has = jo.RunMethod("isExternalStorageManager", Null)
	Return has
End Sub

Public Sub GetPermission
	If HasPermission Then
		RaiseEvent
		Return
	End If
	Dim in As Intent
	in.Initialize("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", "package:" & Application.PackageName)
	StartActivityForResult(in)
End Sub

Private Sub RaiseEvent
	CallSubDelayed(mCallback, mEventName & "_StorageAvailable")
End Sub

Private Sub ion_Event (MethodName As String, Args() As Object) As Object
	RaiseEvent
	Return Null
End Sub

Private Sub StartActivityForResult(i As Intent)
	Dim jo As JavaObject = GetBA
	ion = jo.CreateEvent("anywheresoftware.b4a.IOnActivityResult", "ion", Null)
	jo.RunMethod("startActivityForResult", Array As Object(ion, i))
End Sub

Private Sub GetBA As Object
	Dim jo As JavaObject = Me
	Return jo.RunMethod("getBA", Null)
End Sub