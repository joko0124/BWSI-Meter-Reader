B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=7.8
@EndOfDesignText@
Sub Class_Globals
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize

End Sub

'also: https://www.b4x.com/android/forum/threads/print-image-graphic-with-esc-pos-epson.73456/
'Original code: https://www.b4x.com/android/forum/threads/send-bitmap-data-to-esc-pos-printer.73599/#post-467662
Sub getBytesForBitmap( bitmapName As String) As Byte()
	
	Dim myBitmap As Bitmap
	myBitmap = LoadBitmap(File.DirAssets, bitmapName)
	
	Log("rastering bitmap: width="&myBitmap.Width&" height="&myBitmap.Height)

	Dim out As OutputStream
	out.InitializeToBytesArray(0)						' define start size (0)
	
	out.WriteBytes(Array As Byte(0x1B,0x40),0,2)     	' ESC @     	Initialize printer
	
	out.WriteBytes(Array As Byte(0x1B,0x33,33),0,3)     ' ESC '3' 24 	Set n vertical motion units (set by GS P) line spacing (n=24)

	' 300x300 --> cada linia es: 5 (header) + 300*24
	' En total tindrem 13 linies ( 300/24 arrodonit cap a dalt)
	' Total = 5 + 13*( 5 + 300*24))

	Dim offset As Int=0
	
	Do While offset<myBitmap.Height
		
		out.WriteBytes(Array As Byte(0x1B,0x2A,33),0,3)		' ESC * 33	Bit image mode, 24-dot double density (es el 33) --> 200dpi
		Dim wl As Int = Bit.And(myBitmap.Width,0xFF)
		Dim wh As Int = Bit.ShiftRight(myBitmap.Width,8)
		out.WriteBytes(Array As Byte(wl,wh),0,2)	' Width_low, Width_high
	
		For x=0 To myBitmap.Width-1
		
			For k=0 To 2
					
				Dim slice As Byte=0
				For b=0 To 7
					Dim y As Int = offset+8*k+b
					If y<myBitmap.Height Then
						Dim pixel As Int = myBitmap.GetPixel(x,y)
						If Bit.And(pixel,0xFFFFFF)<>0xFFFFFF Then
							slice = Bit.Or(slice, Bit.ShiftRight(0x80,b))
						End If
					End If
					
				Next
				out.WriteBytes(Array As Byte(slice),0,1)
		
			Next
		Next
		offset=offset+24
		out.WriteBytes(Array As Byte(0x0A),0,1)			' \n after each line
	Loop

	out.WriteBytes(Array As Byte(0x1B,0x33,33),0,3)		' ESC '3' 30	 restore line spacing

	Return out.ToBytesArray()

End Sub
	
	
Sub getBytesForBitmap2( bitmapName As String , centered As Boolean ) As Byte()
		
	
	Dim myBitmap As Bitmap, x As Int
	myBitmap = LoadBitmap(File.DirAssets, bitmapName)
	
	Log("rastering2 bitmap: width="&myBitmap.Width&" height="&myBitmap.Height)

	Dim out As OutputStream
	out.InitializeToBytesArray(0)						' define start size (0)
	
	out.WriteBytes(Array As Byte(0x1B,0x40),0,2)     	' ESC @     	Initialize printer

	If centered Then
		out.WriteBytes(Array As Byte(0x1B,0x61,1),0,3)     ' ESC a  '1'	Center justification
	End If
	
	'0x1D, 0x2A, 0, 0
	out.WriteBytes(Array As Byte(0x1D,0x76,0x30,0x30),0,4)		' ESC * 33	Bit image mode, 24-dot double density (es el 33) --> 200dpi
	'out.WriteBytes(Array As Byte(GS,v,0,0),0,4)
	Dim mbytes As Int = (myBitmap.Width+7)/8
	Dim wl As Int = Bit.And(mbytes,0xFF)
	Dim wh As Int = Bit.ShiftRight(mbytes,8)
	Dim hl As Int = Bit.And(myBitmap.height,0xFF)
	Dim hh As Int = Bit.ShiftRight(myBitmap.height,8)
	
	out.WriteBytes(Array As Byte(wl,wh,hl,hh),0,4)	' Width_low, Width_high
			
	For y=0 To myBitmap.Height-1
		
		For x=0 To myBitmap.Width-1 Step 8
			Dim slice As Byte=0
			For b=0 To 7
				If x+b < myBitmap.Width Then
					If Bit.And( myBitmap.GetPixel(x+b,y),0xFFFFFF)<>0xFFFFFF Then
						slice=Bit.Or(slice,Bit.ShiftRight(0x80,b))
					End If
				End If
			Next
			out.WriteBytes(Array As Byte(slice),0,1)
		Next
	Next

	Return out.ToBytesArray()

End Sub
	

' Adds N dots at the left in order to justofty it. The number of dots to 'center' the image will depend
' on the printer's max dots per line and the picture width
' This number should be a multiple of 8 for speed. Otherwise, we do it
Sub getBytesForBitmap3( bitmapName As String , dotsAtTheLeft As Int ) As Byte()
		
	
	Dim myBitmap As Bitmap, x As Int
	myBitmap = LoadBitmap(File.DirAssets, bitmapName)
	
	Log("rastering2 bitmap: width="&myBitmap.Width&" height="&myBitmap.Height)

	Dim out As OutputStream
	out.InitializeToBytesArray(0)						' define start size (0)
	
	out.WriteBytes(Array As Byte(0x1B,0x40),0,2)     	' ESC @     	Initialize printer

	dotsAtTheLeft = Bit.And(dotsAtTheLeft,0xFFF8)
	
	out.WriteBytes(Array As Byte(0x1D,0x76,0x30,0x30),0,4)		' ESC * 33	Bit image mode, 24-dot double density (es el 33) --> 200dpi
	Dim mbytes As Int = (myBitmap.Width+7)/8 + dotsAtTheLeft/8
	Dim wl As Int = Bit.And(mbytes,0xFF)
	Dim wh As Int = Bit.ShiftRight(mbytes,8)
	Dim hl As Int = Bit.And(myBitmap.height,0xFF)
	Dim hh As Int = Bit.ShiftRight(myBitmap.height,8)
	
	out.WriteBytes(Array As Byte(wl,wh,hl,hh),0,4)	' Width_low, Width_high
			
	For y=0 To myBitmap.Height-1
		
		For k=0 To (dotsAtTheLeft/8)-1
			out.WriteBytes(Array As Byte(0x00),0,1)
		Next
		
		For x=0 To myBitmap.Width-1 Step 8
			Dim slice As Byte=0
			For b=0 To 7
				If x+b < myBitmap.Width Then
					If Bit.And( myBitmap.GetPixel(x+b,y),0xFFFFFF)<>0xFFFFFF Then
						slice=Bit.Or(slice,Bit.ShiftRight(0x80,b))
					End If
				End If
			Next
			out.WriteBytes(Array As Byte(slice),0,1)
		Next
	Next

	Return out.ToBytesArray()

End Sub

