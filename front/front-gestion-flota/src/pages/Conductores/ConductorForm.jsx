import { useEffect, useState } from "react";
import "./ConductorForm.css";

function ConductorForm({ onSubmit, conductor }) {

    const initialForm = {
    rut: "",
    nombre: "",
    telefono: "",
    numeroLicencia: "",
    claseLicencia: "",
    fechaVencimientoLicencia: ""
};

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {

        if (conductor) {
            setForm(conductor);
        } else {
            setForm(initialForm);
        }

        setErrores({});

    }, [conductor]);

    const handleChange = (e) => {

        const { name, value } = e.target;

        setForm({
            ...form,
            [name]: value
        });

        setErrores({
            ...errores,
            [name]: ""
        });

    };

    const validar = () => {

        const errores = {};
        const rutRegex = /^[0-9]{1,2}\.[0-9]{3}\.[0-9]{3}-[0-9Kk]$/;
        const telefonoRegex = /^9[0-9]{8}$/;

        if (!form.rut.trim()) {
            errores.rut = "El RUT es obligatorio.";
        } else if (!rutRegex.test(form.rut)) {
            errores.rut = "Formato válido: 12.345.678-9";
        }

        if (!form.claseLicencia) {
            errores.claseLicencia =
                "Seleccione la clase de licencia.";
        }

        if (!form.nombre.trim()) {
            errores.nombre = "El nombre es obligatorio.";
        } else if (form.nombre.trim().length < 3) {
            errores.nombre = "Debe tener al menos 3 caracteres.";
        }

        if (!form.telefono.trim()) {
            errores.telefono = "El teléfono es obligatorio.";
        } else if (!telefonoRegex.test(form.telefono)) {

            errores.telefono = "Debe tener 9 dígitos (ej: 912345678).";
        }
        if (
            form.numeroLicencia === null ||
            form.numeroLicencia === undefined ||
            form.numeroLicencia === ""
        ) {
            errores.numeroLicencia = "Ingrese el número de licencia.";
        }

        if (!form.fechaVencimientoLicencia) {
            errores.fechaVencimientoLicencia =
                "Seleccione la fecha de vencimiento.";
        } else {
            const hoy = new Date();
            hoy.setHours(0,0,0,0);

            const fecha = new Date(form.fechaVencimientoLicencia);
            if (fecha < hoy) {
                errores.fechaVencimientoLicencia =
                    "La licencia ya está vencida.";
            }
        }

        setErrores(errores);

        return Object.keys(errores).length === 0;

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        console.log("Formulario:", form);

    const esValido = validar();

    console.log("¿Es válido?", esValido);

        if (!validar()) return;

        console.log("Enviando...");

        onSubmit(form);

    };

    return (

        <form
            className="conductor-form"
            onSubmit={handleSubmit}
        >

            <div>

                <input
                    name="rut"
                    placeholder="Rut"
                    value={form.rut}
                    onChange={handleChange}
                />

                {errores.rut &&
                    <span className="error">{errores.rut}</span>
                }

            </div>

            <div>

                <input
                    name="nombre"
                    placeholder="Nombre"
                    maxLength={100}
                    value={form.nombre}
                    onChange={handleChange}
                />

                {errores.nombre &&
                    <span className="error">{errores.nombre}</span>
                }

            </div>

            <div>

                <input
                    name="telefono"
                    placeholder="Teléfono"
                    maxLength={9}
                    value={form.telefono}
                    onChange={handleChange}
                />

                {errores.telefono &&
                    <span className="error">{errores.telefono}</span>
                }

            </div>
            <div>
                <select
                    name="claseLicencia"
                    value={form.claseLicencia}
                    onChange={handleChange}
                >
                    <option value="">
                        Seleccione la clase
                    </option>

                    <option value="A2">
                        A2
                    </option>
                    <option value="A3">
                        A3
                    </option>
                    <option value="A4">
                        A4
                    </option>
                    <option value="A5">
                        A5
                    </option>
                </select>

                {
                    errores.claseLicencia &&
                    <span className="error">
                        {errores.claseLicencia}
                    </span>
                }

            </div>

            <div>

                <input
                    name="numeroLicencia"
                    placeholder="Número Licencia"
                    maxLength={30}
                    value={form.numeroLicencia}
                    onChange={handleChange}
                />

                {errores.numeroLicencia &&
                    <span className="error">{errores.numeroLicencia}</span>
                }

            </div>

            <div>

                <input
                    type="date"
                    name="fechaVencimientoLicencia"
                    value={form.fechaVencimientoLicencia}
                    onChange={handleChange}
                />

                {errores.fechaVencimientoLicencia &&
                    <span className="error">
                        {errores.fechaVencimientoLicencia}
                    </span>
                }

            </div>

            <button type="submit">

                {conductor ? "Actualizar" : "Guardar"}

            </button>

        </form>

    );

}

export default ConductorForm;