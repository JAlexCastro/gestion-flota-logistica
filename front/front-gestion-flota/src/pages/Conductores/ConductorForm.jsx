import { useEffect, useState } from "react";
import "./ConductorForm.css";

function ConductorForm({ onSubmit, conductor }) {

    const [form, setForm] =useState({
        rut:"",
        nombre:"",
        telefono:"",
        numeroLicencia:"",
        fechaVencimientoLicencia:""
    });

    useEffect(() => {

        if(conductor){

            setForm(conductor);

        }else{

            setForm({
                rut:"",
                nombre:"",
                telefono:"",
                numeroLicencia:"",
                fechaVencimientoLicencia:""
            });

        }

    },[conductor]);

    const handleChange=(e)=>{

        setForm({
            ...form,
            [e.target.name]:e.target.value
        });

    }

    const handleSubmit=(e)=>{

        e.preventDefault();

        onSubmit(form);

    }

    return(

        <form
            className="conductor-form"
            onSubmit={handleSubmit}
        >

            <input
                name="rut"
                placeholder="Rut"
                value={form.rut}
                onChange={handleChange}
            />

            <input
                name="nombre"
                placeholder="Nombre"
                value={form.nombre}
                onChange={handleChange}
            />

            <input
                name="telefono"
                placeholder="Teléfono"
                value={form.telefono}
                onChange={handleChange}
            />

            <input
                name="numeroLicencia"
                placeholder="Número Licencia"
                value={form.numeroLicencia}
                onChange={handleChange}
            />

            <input
                type="date"
                name="fechaVencimientoLicencia"
                value={form.fechaVencimientoLicencia}
                onChange={handleChange}
            />

            <button type="submit">

                {conductor ? "Actualizar" : "Guardar"}

            </button>

        </form>

    );

}

export default ConductorForm;