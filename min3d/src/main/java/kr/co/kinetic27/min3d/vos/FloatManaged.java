package kr.co.kinetic27.min3d.vos;

import  kr.co.kinetic27.min3d.interfaces.IDirtyParent;

public class FloatManaged extends AbstractDirtyManaged 
{
	private float _f;

	public FloatManaged(float $value, IDirtyParent $parent)
	{
		super($parent);
		set($value);
	}
	
	public float get()
	{
		return _f;
	}
	public void set(float $f)
	{
		_f = $f;
		setDirtyFlag();
	}
}
